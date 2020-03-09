package com.example.hopinnow.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.gson.Gson;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback, RiderProfileStatusListener {

    private GoogleMap mMap;
    MapFragment mapFragment;

    //TODO change to current location later on pickUpLoc

    //TODO for map focus on fragment switch
    private Boolean searchInPlace;
    private Rider rider;
    private Driver driver;
    private LatLng pickUpLoc = new LatLng(53.631611, -113.323975);
    private LatLng dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Marker pickUpMarker, dropOffMarker;
    private Request curRequest;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.map_key));
        }


        setContentView(R.layout.activity_rider_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        searchInPlace = true;
        driver = null;

        setupAutoCompleteFragment();

        Button addRequest = findViewById(R.id.add_request_button);
        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO [BUG] if both locations eneterd, then one location cleared, validation below would not work
                if ((pickUpLoc!=null)&&(dropOffLoc!=null)){
                    setRequest();
                } else {
                    Toast.makeText(RiderMapActivity.this, "Please enter both your pick up and drop off locations.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getRiderProfile(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLoc, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(pickUpLoc)
                .title("My Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    //called in oncreate to set up autocomplete fragments
    private void setupAutoCompleteFragment() {

        AutocompleteSupportFragment pickUpAutoComplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete);
        pickUpAutoComplete.setHint("Pick Up Location");
        //pickUpAutoComplete.setText("My Current Location");
        pickUpAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        pickUpAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (place!=null){
                    pickUpLocName = place.getAddress();
                    pickUpLoc = place.getLatLng();
                    setMapMarker(pickUpMarker,pickUpLoc);
                }
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });


        final AutocompleteSupportFragment dropOffAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete));
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            //TODO curReqest to firebase trip list

            cancelRequest();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        searchInPlace = false;
        Bundle b = getIntent().getExtras();

        if(b!=null) {
            if (Boolean.parseBoolean(Objects.requireNonNull(b.get("Current_Request_To_Null")).toString())){
                cancelRequest();
            }
        }

        if (curRequest!=null) {
            View searchFragment = findViewById(R.id.search_layout);
            searchFragment.setVisibility(View.GONE);
            searchInPlace = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (curRequest != null) {
            //mMap.clear();
            curRequest = retrieveCurrentRequest();
        }
    }

    public void switchFragment(int caseId){
        FragmentTransaction t;

        switch(caseId){
            case R.layout.fragment_rider_driver_offer:

                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RiderDriverOfferFragment()).commit();
                //View searchFragment = findViewById(R.id.search_layout);
                //searchFragment.setVisibility(View.GONE);
                break;
            case R.layout.fragment_rider_waiting_pickup:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RiderWaitingPickupFragment()).commit();
                break;
            case R.layout.fragment_rider_pickedup:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RiderPickedUpFragment()).commit();
                break;
            case R.layout.fragment_rider_confirm_dropoff:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RiderConfirmDropoffFragment()).commit();
                break;
        }

        //t.addToBackStack(null);
        //t.commit();
    }

    /**
     * Cancels current request of the rider and return to initial location search prompt page
     */
    public void cancelRequest(){
        //clear all fragments
        FrameLayout fl = findViewById(R.id.fragment_place);
        fl.removeAllViews();
        mMap.clear();

        //set curRequest to null
        curRequest = null;
        saveCurrentRequest(curRequest);
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

    }

    public void saveCurrentRequest(Request req){
        mPrefs = getSharedPreferences("LocalRequest", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(req); // myObject - instance of MyObject
        prefsEditor.putString("CurrentRequest", json);
        prefsEditor.apply();
    }

    public Request retrieveCurrentRequest(){
        Gson gson = new Gson();
        String json = mPrefs.getString("CurrentRequest", "");
        return gson.fromJson(json, Request.class);
    }

    public void setRequest(){
        Date dateTime = Calendar.getInstance().getTime();
        EstimateFare fare = new EstimateFare();
        Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc,dateTime);

        //TODO set current Request
        curRequest = new Request( driver,rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, dateTime,null, estimatedFare);

        saveCurrentRequest(curRequest);

        //TODO save cur Request to firebase

        //TODO change intent to new activity
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.GONE);
        searchInPlace = true;
        switchFragment(R.layout.fragment_rider_driver_offer);
    }

    public void callNumber(String phoneNumber){

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(RiderMapActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void emailDriver(String email){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

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
      * adjust focus of the map according to the markers
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


    @Override
    public void onRiderProfileRetrieveSuccess(Rider rider) {
        this.rider = rider;
    }

    @Override
    public void onRiderProfileRetrieveFailure() {

    }
}
