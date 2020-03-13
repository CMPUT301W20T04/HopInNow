package com.example.hopinnow.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.hopinnow.database.RequestDatabaseAccessor;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.R;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.EstimateFare;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.LatLong;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderRequestAcceptedListener;
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * Author: Tianyu Bai
 * This activity defines all methods for main activities of rider.
 */
public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback,
        RiderProfileStatusListener, RiderRequestAcceptedListener, DriverObjectRetreieveListener,
        AvailRequestListListener{

    public static final String TAG = "RiderMapActivity";
    private GoogleMap mMap;
    private SharedPreferences mPrefs;
    private Boolean searchInPlace; //for map zoom

    private Rider rider;
    private Driver driver;
    private Request curRequest;

    //TODO change to current location later on pickUpLoc
    private LatLng pickUpLoc = new LatLng(53.631611, -113.323975);
    private LatLng dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    //TODO DRAG MARKER TO PIN LOCATION, onMarkerDragListener
    private Marker pickUpMarker, dropOffMarker;

    private DriverDatabaseAccessor dDA;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    private RequestDatabaseAccessor rDA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //rider = (Rider) getIntent().getSerializableExtra("RiderObject");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // initialize places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.map_key));
        }

        // assign logged in rider to local variable
        riderDatabaseAccessor = new RiderDatabaseAccessor();
        riderDatabaseAccessor.getRiderProfile(this);

        dDA = new DriverDatabaseAccessor();

        rDA = new RequestDatabaseAccessor();

        // sets map
        setContentView(R.layout.activity_rider_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        // sets variable
        searchInPlace = true;
        driver = null;

        // sets location search bars
        setupAutoCompleteFragment();

        //MOCK FOR INTENT TESTING
        final EditText pickUpMock = findViewById(R.id.mock_pickUp);
        final EditText dropOffMock = findViewById(R.id.mock_dropOff);

        // sets button for adding new request
        Button addRequestBtn = findViewById(R.id.add_request_button);
        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //mock, for UI test
                if ((!dropOffMock.getText().toString().equals(""))&&(!pickUpMock.getText().toString().equals(""))){
                    pickUpLocName = pickUpMock.getText().toString();
                    pickUpLoc = new LatLng(53.5258, 113.5207);
                    dropOffLocName = dropOffMock.getText().toString();
                    dropOffLoc = new LatLng(53.5224, 113.5305);
                }

                //FIXME
                // if both locations eneterd, then one cleared, validation below would not work
                // maybe gettext in autocompletefragment for validation
                if ((pickUpLoc!=null)&&(dropOffLoc!=null)){
                    switchMarkerDraggable();
                    setNewRequest();

                } else {
                    String msg = "Please enter both your pick up and drop off locations.";
                    Toast.makeText(RiderMapActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // sets button for viewing rider profile
        FloatingActionButton riderMenuBtn = findViewById(R.id.riderMenuBtn);
        riderMenuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RiderMapActivity.this,RiderMenuActivity.class);
                startActivity(intent);
            }
        });


        //TODO listener on rider curRequest,
        // save waiting driver offer status, calls switch fragment
        // update curRequest by retrieveCurrentRequestOnline
    }


    /**
     * Sets up initlal map.
     * @param googleMap
     *      map object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //TODO CURRENT LOCATION
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLoc, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(pickUpLoc)
                .title("My Current Location")
                .icon(toBitmapMarkerIcon(getResources().getDrawable(R.drawable.marker_pick_up)))
                .draggable(true));
    }


    /**
     * Sets up auto complete fragment from Google Places API.
     */
    private void setupAutoCompleteFragment() {

        // fragment for pick up locaation
        AutocompleteSupportFragment pickUpAutoComplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete);
        assert pickUpAutoComplete != null;
        pickUpAutoComplete.setHint("Pick Up Location");
        //pickUpAutoComplete.setText("My Current Location");
        pickUpAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        pickUpAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                pickUpLocName = place.getAddress();
                pickUpLoc = place.getLatLng();
                setMapMarker(pickUpMarker,pickUpLoc,true);
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });

        // fragment for drop off location
        final AutocompleteSupportFragment dropOffAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete));
        assert dropOffAutoComplete != null;
        dropOffAutoComplete.setHint("Drop Off Location");
        dropOffAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        dropOffAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                dropOffLocName = place.getAddress();
                dropOffLoc = place.getLatLng();
                setMapMarker(dropOffMarker,dropOffLoc,false);
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });
    }


    /**
     * Displays appropriate content according to the presence of current request.
     */
    @Override
    protected void onStart(){
        super.onStart();
        String caseCancel = getIntent().getStringExtra("Current_Request_To_Null");

        if (caseCancel =="cancel") {
            cancelRequestLocal();
        }

        if (curRequest!=null) {
            View searchFragment = findViewById(R.id.search_layout);
            curRequest = retrieveCurrentRequestLocal();
            searchFragment.setVisibility(View.GONE);
            //MOCK
            findViewById(R.id.mock).setVisibility(View.GONE);
            searchInPlace = false;
        } else {
            searchInPlace = true;
        }

        switchMarkerDraggable();

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
        mMap.clear();

        //set curRequest to null
        curRequest = null;
        saveCurrentRequestLocal(null);
        pickUpLocName = null;
        dropOffLocName= null;
        pickUpLoc = null;
        dropOffLoc = null;
        pickUpMarker = null;
        dropOffMarker = null;

        //return to initial prompt of location searching
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.VISIBLE);
        ((AutocompleteSupportFragment) Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.pick_up_auto_complete))).setText(pickUpLocName);
        ((AutocompleteSupportFragment) Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.drop_off_auto_complete))).setText(dropOffLocName);

        // TODO CURRENT LOCATION ZOOM AND MARKER

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
        String emailDriver = curRequest.getDriverEmail();
        dDA.getDriverObject(emailDriver,this);
        return driver;
    }


    /**
     * Creates new request and save it to both locally and online.
     */
    public void setNewRequest(){
        Date dateTime = Calendar.getInstance().getTime();
        EstimateFare fare = new EstimateFare();
        Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc,dateTime);

        //TODO set current Request
        // FIXME, changed driver to driver.email():
        LatLong latLongP = new LatLong(pickUpLoc.latitude, pickUpLoc.longitude);
        LatLong latLongD = new LatLong(dropOffLoc.latitude, dropOffLoc.longitude);
        curRequest = new Request(null, rider.getEmail(), latLongP, latLongD, pickUpLocName,
                dropOffLocName, dateTime,null, estimatedFare);

        saveCurrentRequestLocal(curRequest);

        //TODO save cur Request to firebase
        rDA.addRequest(curRequest,this);

        switchFragment(R.layout.fragment_rider_waiting_driver);

        //TODO change intent to new activity
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.GONE);

        //Mock
        findViewById(R.id.mock).setVisibility(View.GONE);

        //searchInPlace = true;
        //switchFragment(R.layout.fragment_rider_waiting_driver);
    }


    /**
     * Starts phone calling.
     * @param phoneNumber
     *      the phone number to be called
     */
    public void callNumber(String phoneNumber){
        //TODO HANGING UP DIALING PAGE
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(RiderMapActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO PERMISSION DIALOG
            return;
        }
        startActivity(callIntent);
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
     public void setMapMarker(Marker m, LatLng latLng, Boolean pickUp){
         BitmapDescriptor mIcon;
         if (pickUp){
             mIcon = toBitmapMarkerIcon(getResources().getDrawable(R.drawable.marker_pick_up));
         } else {
             mIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
         }

         if (m == null) {
             MarkerOptions opt = new MarkerOptions();
             opt.position(latLng)
                .icon(mIcon)
                .draggable(true);
             mMap.addMarker(opt);
         } else {
             m.setPosition(latLng);
         }
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

         //TODO CHANGE PADDING ACCORDING TO FRAGMents

         mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bound.build(), 300));
     }


    /**
     * Shows driver information and contact means on a dialog
     */
    public void showDriverInfo(Driver myDriver){
        //TODO make into a helper class for payment rating dialog, name on click
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
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(d.getPhoneNumber());
            }
        });

        //email driver
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDriver(d.getEmail());
            }
        });

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


    /**
     * Called when profile retrieve successfully:
     * @param rider
     *      receives a user object containing all info about the current user.
     */
    @Override
    public void onRiderProfileRetrieveSuccess(Rider rider) {
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
        FragmentManager t = getSupportFragmentManager();
        t.beginTransaction().replace(R.id.fragment_place, new RiderWaitingPickupFragment())
                .commit();
    }

    @Override
    public void onRiderRequestTimeoutOrFail() {
        FragmentManager t = getSupportFragmentManager();
        t.popBackStack();
    }




    @Override
    public void onDriverObjRetrieveSuccess(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void onDriverObjRetrieveFailure() {

    }

    @Override
    public void onRequestAddedSuccess() {
        searchInPlace = true;
        //switchFragment(R.layout.fragment_rider_waiting_driver);
    }

    @Override
    public void onRequestAddedFailure() {
        switchFragment(R.layout.fragment_rider_waiting_driver);
    }

    @Override
    public void onRequestDeleteSuccess() {

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
}
