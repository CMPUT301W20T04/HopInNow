package com.example.hopinnow.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.hopinnow.Database.UserDatabaseAccessor;
import com.example.hopinnow.R;
import com.example.hopinnow.entities.EstimateFare;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.google.android.gms.common.api.Status;

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


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * Author: Tianyu Bai
 * This activity defines all methods for main activities of rider.
 */
public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback,
        RiderProfileStatusListener {

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
    private Marker pickUpMarker, dropOffMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.map_key));
        }

        // assign logged in rider to local variable
        UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getRiderProfile(this);

        // sets map
        setContentView(R.layout.activity_rider_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        // sets variable
        searchInPlace = true;
        driver = null;

        // sets location search bars
        setupAutoCompleteFragment();

        // sets button for adding new request
        Button addRequest = findViewById(R.id.add_request_button);
        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO [BUG]
                // if both locations eneterd, then one cleared, validation below would not work
                // maybe gettext in autocompletefragment for validation
                if ((pickUpLoc!=null)&&(dropOffLoc!=null)){
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLoc, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(pickUpLoc)
                .title("My Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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
                setMapMarker(pickUpMarker,pickUpLoc);
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
                setMapMarker(dropOffMarker,dropOffLoc);
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });
    }




    /**
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            //TODO curReqest to firebase trip list
            cancelRequestLocal();
        }
    }*/


    /**
     * Displays appropriate content.
     */
    @Override
    protected void onStart(){
        super.onStart();
        searchInPlace = false;
        Bundle b = getIntent().getExtras();

        if(b!=null) {
            if (Boolean.parseBoolean(Objects.requireNonNull(b.get("Current_Request_To_Null")).toString())){
                cancelRequestLocal();
            }
        }

        if (curRequest!=null) {
            View searchFragment = findViewById(R.id.search_layout);
            curRequest = retrieveCurrentRequestLocal();
            searchFragment.setVisibility(View.GONE);
            searchInPlace = false;
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
                t.beginTransaction().add(R.id.fragment_place, new RiderWaitingDriverFragment())
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
    //TODO
    public Request retrieveCurrentRequestOnline(){
        return rider.getCurRequest();
    }


    /**
     * Creates new request and save it to both locally and online.
     */
    public void setNewRequest(){
        Date dateTime = Calendar.getInstance().getTime();
        EstimateFare fare = new EstimateFare();
        Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc,dateTime);

        //TODO set current Request
        curRequest = new Request( driver,rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, dateTime,null, estimatedFare);

        saveCurrentRequestLocal(curRequest);

        //TODO save cur Request to firebase

        //TODO change intent to new activity
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.GONE);
        searchInPlace = true;
        switchFragment(R.layout.fragment_rider_waiting_driver);
    }


    /**
     * Starts phone calling.
     * @param phoneNumber
     *      the phone number to be called
     */
    public void callNumber(String phoneNumber){
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
         if (m == null) {
             MarkerOptions opt = new MarkerOptions();
             opt.position(latLng);
             mMap.addMarker(opt);
         } else {
             m.setPosition(latLng);
         }
         adjustMapFocus();
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
    public void showDriverInfo(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_driver_info);

        //set driver name
        TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
        driverName.setText(driver.getName());

        //set driver rating
        TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
        String rating;
        if (driver.getRating()==-1){
            rating = "not yet rated";
        } else {
            rating = Double.toString(driver.getRating());
        }
        driverRating.setText(rating);

        //set driver car
        TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
        String carInfo = driver.getCar().getColor() + " " + driver.getCar().getMake() + " " + driver.getCar().getModel();
        driverCar.setText(carInfo);

        //set driver license
        TextView driverLicense = dialog.findViewById(R.id.dialog_driver_plate);
        driverLicense.setText(driver.getCar().getPlateNumber());

        //call driver
        Button callBtn= dialog.findViewById(R.id.dialog_call_button);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(driver.getPhoneNumber());
            }
        });

        //email driver
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDriver(driver.getEmail());
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
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
     * Called when profile retrieve failed:
     */
    @Override
    public void onRiderProfileRetrieveFailure() {}

}
