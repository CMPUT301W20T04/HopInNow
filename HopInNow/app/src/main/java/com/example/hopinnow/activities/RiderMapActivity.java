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

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.EstimateFare;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Driver;
import com.google.android.gms.common.api.Status;


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


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapFragment mapFragment;
    //TODO change to current location later on pickUpLoc
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private Button addRequest;

    private Rider rider;
    private Driver driver;
    private LatLng pickUpLoc,dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Marker pickUpMarker, dropOffMarker;
    private Request curRequest;

    private SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        //TODO set rider, driver, car properly
        rider = new Rider();
        Car car = new Car("Auburn","Speedster","Cream","111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third", "12345678", true, null, car, null, null);


        setupAutoCompleteFragment();

        addRequest = findViewById(R.id.add_request_button);
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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(edmonton) //set to current location later on pickUpLoc
                .title("Edmonton")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    //called in oncreate to set up autocomplete fragments
    private void setupAutoCompleteFragment() {
        //initialize autocomplete fragments

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.map_key));
        }

        AutocompleteSupportFragment pickUpAutoComplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete);
        Objects.requireNonNull(pickUpAutoComplete).setHint("Pick Up Location");
        pickUpAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        pickUpAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if (place!=null){
                    pickUpLocName = place.getAddress();
                    pickUpLoc = place.getLatLng();
                    //pickUpMarker.setPosition(pickUpLoc);
                    //pickUpMarker.setTitle("Pick Up Location");
                    //unsure if this is needed for update
                    //mapFragment.getMapAsync(RiderMapActivity.this);
                }
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });


        final AutocompleteSupportFragment dropOffAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete));
        Objects.requireNonNull(dropOffAutoComplete).setHint("Drop Off Location");
        dropOffAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS, Place.Field.NAME,Place.Field.LAT_LNG));
        dropOffAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if (place!=null){
                    dropOffLocName = place.getAddress();
                    dropOffLoc = place.getLatLng();
                    //dropOffMarker.setPosition(dropOffLoc);
                    //dropOffMarker.setTitle("Drop Off Location");
                    //unsure if this is needed for update
                    //mapFragment.getMapAsync(RiderMapActivity.this);
                }
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
        //curRequest = retrieveCurrentRequest();
        if (curRequest!=null){
            View searchFragment = findViewById(R.id.search_layout);
            searchFragment.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (curRequest != null) {
            //mMap.clear();
            curRequest = retrieveCurrentRequest();
        } else {
            cancelRequest();
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
                t.replace(R.id.fragment_place, new RiderPickedupFragment()).commit();
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

        //set curRequest to null
        curRequest = null;
        saveCurrentRequest(curRequest);
        pickUpLocName = null;
        dropOffLocName= null;
        pickUpLoc = null;
        dropOffLoc = null;

        //return to initial prompt of location searching
        View searchFragment = findViewById(R.id.search_layout);
        searchFragment.setVisibility(View.VISIBLE);
        ((AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete)).setText(pickUpLocName);
        ((AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete)).setText(dropOffLocName);

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
        Request req = gson.fromJson(json, Request.class);
        return req;
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

}
