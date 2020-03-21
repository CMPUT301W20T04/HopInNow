package com.example.hopinnow.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.hopinnow.database.RequestDatabaseAccessor;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.R;

import com.example.hopinnow.database.RiderRequestDatabaseAccessor;
import com.example.hopinnow.entities.EstimateFare;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.LatLong;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderRequestListener;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * Author: Tianyu Bai
 * This activity defines all methods for main activities of rider.
 */
public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback,
        RiderProfileStatusListener, RiderRequestListener, DriverObjectRetreieveListener,
        AvailRequestListListener, LocationListener {

    public static final String TAG = "RiderMapActivity";
    private GoogleMap mMap;
    private SharedPreferences mPrefs;

    private Rider rider;
    private Driver driver;
    private Request curRequest;

    private Location current;
    private LatLng pickUpLoc;
    private LatLng dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Marker pickUpMarker, dropOffMarker;
    private AutocompleteSupportFragment dropOffAutoComplete, pickUpAutoComplete;

    private DriverDatabaseAccessor driverDatabaseAccessor;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    private RequestDatabaseAccessor requestDatabaseAccessor;
    private RiderRequestDatabaseAccessor riderRequestDatabaseAccessor;

    // progress bar here:
    private ProgressbarDialog progressbarDialog;

    @SuppressLint({"CheckResult", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initialize places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.map_key));
        }
        setContentView(R.layout.activity_rider_map);
        // sets variable
        driver = null;
        // sets location search bars
        pickUpAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete));
        dropOffAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete));
        setupAutoCompleteFragment();
        // get current location
        if ((ActivityCompat.checkSelfPermission(RiderMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(RiderMapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(granted -> {
                        if (granted) {
                            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    0, 0, this);
                        }
                    });
        } else {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, this);
        }
        // sets map
        pickUpLoc = new LatLng(53.5258, -113.5207);
        dropOffLoc = new LatLng(53.5224, -113.5305);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);
        // assign logged in rider to local variable
        this.riderDatabaseAccessor = new RiderDatabaseAccessor();
        this.driverDatabaseAccessor = new DriverDatabaseAccessor();
        this.requestDatabaseAccessor = new RequestDatabaseAccessor();
        this.riderRequestDatabaseAccessor = new RiderRequestDatabaseAccessor();
        // let the progress bar show:
        this.progressbarDialog = new ProgressbarDialog(this);
        this.progressbarDialog.startProgressbarDialog();
        this.riderDatabaseAccessor.getRiderProfile(this);
    }


    /**
     * Displays appropriate content according to the presence of current request.
     */
    @Override
    protected void onStart(){
        super.onStart();
        String caseCancel = getIntent().getStringExtra("Current_Request_To_Null");
        // MOCK FOR INTENT TESTING
        final EditText pickUpMock = findViewById(R.id.mock_pickUp);
        final EditText dropOffMock = findViewById(R.id.mock_dropOff);
        // sets button for adding new request
        Button addRequestBtn = findViewById(R.id.add_request_button);
        addRequestBtn.setOnClickListener(v -> {
            //mock, for UI test
            if ((!dropOffMock.getText().toString().equals(""))&&(!pickUpMock.getText().toString().equals(""))){
                pickUpLocName = pickUpMock.getText().toString();
                pickUpLoc = new LatLng(53.5258, -113.5207);
                dropOffLocName = dropOffMock.getText().toString();
                dropOffLoc = new LatLng(53.5224, -113.5305);
            }
            //FIXME
            // if both locations eneterd, then one cleared, validation below would not work
            // maybe gettext in autocompletefragment for validation
            if ((pickUpLoc!=null)&&(dropOffLoc!=null)){
                if (validLocations()){
                    switchMarkerDraggable();
                    setNewRequest();
                }
            } else {
                String msg = "Please enter both your pick up and drop off locations.";
                Toast.makeText(RiderMapActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        // sets button for viewing rider profile
        FloatingActionButton riderMenuBtn = findViewById(R.id.riderMenuBtn);
        riderMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RiderMapActivity.this,RiderMenuActivity.class);
            startActivity(intent);
        });

        if (Objects.equals(caseCancel, "cancel")) {
            cancelRequestLocal();
        }

        if (curRequest!=null) {
            View searchFragment = findViewById(R.id.search_layout);
            curRequest = retrieveCurrentRequestLocal();
            searchFragment.setVisibility(View.GONE);
            //MOCK
            findViewById(R.id.mock).setVisibility(View.GONE);
        }
    }
    /**
     * Creates new request and save it to both locally and online.
     */
    public void setNewRequest(){
        Date dateTime = Calendar.getInstance().getTime();
        EstimateFare fare = new EstimateFare();
        Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc);

        // set attribute of the request:
        LatLong latLongP = new LatLong(pickUpLoc.latitude, pickUpLoc.longitude);
        LatLong latLongD = new LatLong(dropOffLoc.latitude, dropOffLoc.longitude);
        curRequest = new Request(null, rider.getEmail(), latLongP, latLongD, pickUpLocName,
                dropOffLocName, dateTime,null, estimatedFare);

        saveCurrentRequestLocal(curRequest);

        // save current Request to firebase
        this.progressbarDialog.startProgressbarDialog();
        requestDatabaseAccessor.addRequest(curRequest,this);



    }
    /**
     * Sets up initlal map.
     * @param googleMap
     *      map object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker m) {  }

            @Override
            public void onMarkerDragEnd(Marker m) {
                LatLng newLatLng = m.getPosition();
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(newLatLng.latitude, newLatLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0);

                if (m.getTitle().equals("Pick Up Location")){
                    pickUpLoc = newLatLng;
                    pickUpLocName = address;
                    pickUpAutoComplete.setText(pickUpLocName);
                } else {
                    dropOffLoc = newLatLng;
                    dropOffLocName = address;
                    dropOffAutoComplete.setText(dropOffLocName);
                }
            }

            @Override
            public void onMarkerDrag(Marker m) {     }
        });

        Button zoomIn = findViewById(R.id.map_zoom_in);
        zoomIn.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomIn()));

        Button zoomOut = findViewById(R.id.map_zoom_out);
        zoomOut.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomOut()));

        // CURRENT LOCATION
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLoc, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(pickUpLoc)
                .title("Pick Up Location")
                .visible(false)
                .icon(toBitmapMarkerIcon(getResources().getDrawable(R.drawable.marker_pick_up)))
                .draggable(true));

        dropOffMarker = mMap.addMarker(new MarkerOptions()
                .position(pickUpLoc)
                .title("Drop Off Location")
                .visible(false)
                .icon(toBitmapMarkerIcon(getResources().getDrawable(R.drawable.marker_drop_off)))
                .draggable(true));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pickUpLoc, 10.0f));
    }


    /**
     * Sets up auto complete fragment from Google Places API.
     */
    private void setupAutoCompleteFragment() {
        assert pickUpAutoComplete != null;
        pickUpAutoComplete.setHint("Pick Up Location");
        pickUpAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        pickUpAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                pickUpLocName = place.getAddress();
                pickUpLoc = place.getLatLng();
                setMapMarker(pickUpMarker,pickUpLoc);
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });


        assert dropOffAutoComplete != null;
        dropOffAutoComplete.setHint("Drop Off Location");
        dropOffAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        dropOffAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                dropOffLocName = place.getAddress();
                dropOffLoc = place.getLatLng();
                setMapMarker(dropOffMarker,dropOffLoc);
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });
    }


    /**
     * On resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (curRequest != null) {
            curRequest = retrieveCurrentRequestLocal();
        }
    }


    /**
     * Switch to appropriate fragment for rider's view.
     * @param caseId
     *      information of destination fragment
     */
    public void switchFragment(int caseId){
        FragmentManager t = getSupportFragmentManager();

        // caseID defined by id of the next fragment to show
        switch(caseId){
            case R.layout.fragment_rider_waiting_driver:
                t.beginTransaction().replace(R.id.fragment_place, new RiderWaitingDriverFragment())
                        .commit();
                break;
            case R.layout.fragment_rider_driver_offer:
                t.beginTransaction().replace(R.id.fragment_place, new RiderDriverOfferFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case -1:
                t.popBackStack(); //when rider declines driver offer
                break;
            case R.layout.fragment_rider_waiting_pickup:
                t.beginTransaction()
                        .replace(R.id.fragment_place, new RiderWaitingPickupFragment())
                        .commit();
                break;
            case R.layout.fragment_rider_pickedup:
                t.beginTransaction()
                        .replace(R.id.fragment_place, new RiderPickedUpFragment())
                        .commit();
                break;
            case R.layout.fragment_rider_confirm_dropoff:
                t.beginTransaction()
                        .replace(R.id.fragment_place, new RiderConfirmDropOffFragment())
                        .commit();
                break;
        }
    }


    /**
     * Cancels current request of the rider and return to initial location search prompt page.
     */
    public void cancelRequestLocal(){
        //clear all fragments
        FrameLayout fl = findViewById(R.id.fragment_place);
        fl.removeAllViews();

        //set curRequest to null
        curRequest = null;
        saveCurrentRequestLocal(null);
        pickUpLocName = null;
        dropOffLocName= null;
        pickUpLoc = null;
        dropOffLoc = null;
        pickUpMarker.setVisible(false);
        dropOffMarker.setVisible(false);
        switchMarkerDraggable();

        //return to initial prompt of location searching
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.VISIBLE);
        ((AutocompleteSupportFragment) Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.pick_up_auto_complete))).setText(pickUpLocName);
        ((AutocompleteSupportFragment) Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.drop_off_auto_complete))).setText(dropOffLocName);

    }


    /**
     * Save current request locally.
     * @param req
     *      information of current request
     */
    public void saveCurrentRequestLocal(Request req){
        mPrefs = getSharedPreferences("LocalRequest", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(req); // myObject - instance of MyObject
        prefsEditor.putString("CurrentRequest", json).apply();
    }


    /**
     * Retrieves current request form local storage.
     * @return
     *      information of current request
     */
    public Request retrieveCurrentRequestLocal(){
        Gson gson = new Gson();
        String json = mPrefs.getString("CurrentRequest", "");
        return gson.fromJson(json, Request.class);
    }


    /**
     * Retrieves information of the current request from online.
     * @return
     *      current request information from firebase
     */
    public Request retrieveCurrentRequest(){
        return curRequest;
    }

    public Driver retrieveOfferedDriver(){
        return driver;
    }


    /**
     * Starts phone calling.
     * @param phoneNumber
     *      the phone number to be called
     */
    @SuppressLint("CheckResult")
    public void callNumber(String phoneNumber){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(RiderMapActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.CALL_PHONE)
                    .subscribe(granted -> {
                        if (granted) {
                            startActivity(callIntent);
                        }
                    });
        } else {
            String driverNumber = driver.getPhoneNumber();
            Toast.makeText(this,"Driver's Number: " + driverNumber,
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Prompts email app selection and directs to email drafting page with auto0filled email address
     * of the driver.
     * @param email
     *      the driver's email address
     */
    public void emailDriver(String email){
        //Stackoverflow post by Dira
        //https://stackoverflow.com/questions/8701634/send-email-intent
        //Answer by Dira (code from the question itself)
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});

        startActivity(Intent.createChooser(intent, "Send Email"));
    }


    /**
     * Sets marker on the map.
     * @param m
     *      marker object
     * @param latLng
     *      location information for where the given marker object is to be set on the map
     */
     public void setMapMarker(Marker m, LatLng latLng){
         m.setVisible(true);
         m.setPosition(latLng);
         adjustMapFocus();
     }


    /**
     * Sets appropraite draggable state on map markers.
     */
    private void switchMarkerDraggable(){
        if ((pickUpMarker!=null)&&(dropOffMarker!=null)){
            if (pickUpMarker.isDraggable()){
                pickUpMarker.setDraggable(false);
            } else {
                pickUpMarker.setDraggable(true);
            }
            if (dropOffMarker.isDraggable()){
                dropOffMarker.setDraggable(false);
            } else {
                dropOffMarker.setDraggable(true);
            }
        }

    }


     /**
      * Adjust focus of the map according to the markers.
      */
     public void adjustMapFocus(){
         LatLngBounds.Builder bound = new LatLngBounds.Builder();

         if ((pickUpLoc != null)&&(dropOffLoc != null)) {
             bound.include(pickUpLoc);
             bound.include(dropOffLoc);
         } else if (pickUpLoc != null) {
             bound.include(pickUpLoc);
         } else if (dropOffLoc != null) {
             bound.include(dropOffLoc);
         } else {
             return;
         }

         mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound.build(), 300));
     }


    /**
     * Shows driver information and contact means on a dialog
     */
    public void showDriverInfo(Driver myDriver){
        final Driver d = myDriver;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_driver_info);

        //set driver name
        TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
        driverName.setText(d.getName());

        //set driver rating
        TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
        String rating;
        if (d.getRating()==0){
            rating = "not yet rated";
        } else {
            rating = Double.toString(d.getRating());
        }
        driverRating.setText(rating);

        //set driver car
        TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
        String carInfo = d.getCar().getColor() + " " + d.getCar().getMake() + " " + d.getCar().getModel();
        driverCar.setText(carInfo);

        //set driver license
        TextView driverLicense = dialog.findViewById(R.id.dialog_driver_plate);
        driverLicense.setText(d.getCar().getPlateNumber());

        //call driver
        Button callBtn= dialog.findViewById(R.id.dialog_call_button);
        callBtn.setOnClickListener(v -> callNumber(d.getPhoneNumber()));

        //email driver
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(v -> emailDriver(d.getEmail()));

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * Transforms drawable into a bitmap drawable.
     * @param drawable
     *      information of the drawable to be turned into bitmap
     * @return
     *      Bitmap image for marker icon
     */
    private BitmapDescriptor toBitmapMarkerIcon(Drawable drawable) {
        //Stackoverflow post by Mohammed Haidar
        //https://stackoverflow.com/questions/35718103/
        //      how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
        //Answered by Rohit Bansal
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()
                , drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private Boolean validLocations(){
        float distance;
        Location from = new Location(LocationManager.GPS_PROVIDER);
        from.setLatitude(pickUpLoc.latitude);
        from.setLongitude(pickUpLoc.longitude);
        Location to  = new Location(LocationManager.GPS_PROVIDER);
        to.setLongitude(dropOffLoc.longitude);
        to.setLatitude(dropOffLoc.latitude);

        if (current!=null){
            distance = current.distanceTo(from)/1000;
            if (distance>3){
                Toast.makeText(getApplicationContext(),"Your pick up location is too far" +
                        " from your current location. Please reselect.", Toast.LENGTH_SHORT)
                .show();
                return false;
            }
        }

        distance = from.distanceTo(to)/1000;
        if (distance>300){
            Toast.makeText(getApplicationContext(),"Your drop off location is too far" +
                    " from your pick up location. Please reselect.", Toast.LENGTH_SHORT)
            .show();
            return false;
        }

        return true;
    }


    /**
     * Called when profile retrieve successfully:
     * @param rider
     *      receives a user object containing all info about the current user.
     */
    @Override
    public void onRiderProfileRetrieveSuccess(Rider rider) {
        this.progressbarDialog.dismissDialog();
        this.rider = rider;
    }


    /**
     * Called when profile retrieve failed.
     */
    @Override
    public void onRiderProfileRetrieveFailure() {}

    /**
     * Called when profile retrieve successful.
     */
    @Override
    public void onRiderProfileUpdateSuccess(Rider rider) {}

    /**
     * Called to update profile.
     */
    @Override
    public void onRiderProfileUpdateFailure() {}

    /**
     * Disable the back button.
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * We are skipping the step of rider accepting/declining the offer right now.
     */
    @Override
    public void onRiderRequestAcceptedNotify(Request mRequest) {
        curRequest = mRequest;
        String dEmail = curRequest.getDriverEmail();
        driverDatabaseAccessor.getDriverObject(dEmail,this);
    }

    @Override
    public void onRiderRequestTimeoutOrFail() {
//        FragmentManager t = getSupportFragmentManager();
//        t.popBackStack();
    }

    @Override
    public void onRiderPickedupSuccess(Request request) {
        switchFragment(R.layout.fragment_rider_pickedup);
        riderRequestDatabaseAccessor.riderWaitForRequestComplete(this);
    }

    @Override
    public void onRiderPickedupTimeoutOrFail() {

    }

    @Override
    public void onRiderRequestComplete() {
        Toast.makeText(getApplicationContext(), "You have arrived!", Toast.LENGTH_LONG).show();
        //TODO:
        //  Opt 1. change format of availableRequest to trip
        //  Opt 2. move request to new Collection of requestInCompletion with Trip format
        Intent intent = new Intent(getApplicationContext(), RiderPaymentActivity.class);
        startActivity(intent);
        finish();
}

    @Override
    public void onRiderRequestCompletionError() {

    }


    @Override
    public void onDriverObjRetrieveSuccess(Driver driver) {
        this.progressbarDialog.dismissDialog();
        this.driver = driver;
        switchFragment(R.layout.fragment_rider_waiting_pickup);
        riderRequestDatabaseAccessor.riderWaitForPickup(this);
    }

    @Override
    public void onDriverObjRetrieveFailure() {

    }

    @Override
    public void onRequestAddedSuccess() {
        switchFragment(R.layout.fragment_rider_waiting_driver);
        // change intent to new activity
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.GONE);
        //Mock
        findViewById(R.id.mock).setVisibility(View.GONE);
        this.progressbarDialog.dismissDialog();
        riderRequestDatabaseAccessor.riderWaitForRequestAcceptance(this);
    }

    @Override
    public void onRequestAddedFailure() {
    }

    @Override
    public void onRequestDeleteSuccess() {
        Toast.makeText(this,"The request is cancelled succesfully!",Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRequestDeleteFailure() {

    }

    @Override
    public void onGetRequiredRequestsSuccess(ArrayList<Request> requests) {

    }

    @Override
    public void onGetRequiredRequestsFailure() {

    }

    @Override
    public void onLocationChanged(Location location) {
        current = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
