package com.example.hopinnow.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.EstimateFare;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapFragment mapFragment;
    /**change to current location later on pickUpLoc*/
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private Button addRequest;

    private Rider rider;
    private LatLng pickUpLoc,dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Marker pickUpMarker, dropOffMarker;
    private Request curRequest;

    SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        rider = new Rider();

        //initialize autocomplete fragments
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.map_key));
        }
        setupAutoCompleteFragment();


        addRequest = findViewById(R.id.add_request_button);
        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 if ((pickUpLoc!=null)&&(dropOffLoc!=null)){
                     Date dateTime = Calendar.getInstance().getTime();
                     EstimateFare fare = new EstimateFare();
                     Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc,dateTime);

                     //set current Request
                     curRequest = new Request( null,rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, dateTime,null, estimatedFare);

                     SharedPreferences.Editor prefsEditor = mPrefs.edit();
                     Gson gson = new Gson();
                     String json = gson.toJson(curRequest); // myObject - instance of MyObject
                     prefsEditor.putString("CurrentRequest", json);
                     prefsEditor.commit();

                     /**save cur Request to firebase*/

                     /**change intent to new activity*/

                 }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton, 8.5f));
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(edmonton) /**set to current location later on pickUpLoc*/
                .title("Edmonton")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    //called in oncreate to set up autocomplete fragments
    private void setupAutoCompleteFragment() {
        AutocompleteSupportFragment pickUpAutoComplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.pick_up_auto_complete);
        //pickUpAutoComplete.setText("Pick Up Location");
        pickUpAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        pickUpAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
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
            public void onError(Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });
        //set the fragment to selected address
        pickUpAutoComplete.setText(pickUpLocName);


        AutocompleteSupportFragment dropOffAutoComplete = ((AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.drop_off_auto_complete));
        //dropOffAutoComplete.setText("Drop Off Location");
        dropOffAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        dropOffAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
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
            public void onError(Status status) {
                Log.e("An error occurred: ", status.toString());
            }
        });
        //set the fragment to selected address
        dropOffAutoComplete.setText(dropOffLocName);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (curRequest!=null){
            View searchFragment = findViewById(R.id.search_layout);
            searchFragment.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            //mMap.clear();
            Gson gson = new Gson();
            String json = mPrefs.getString("CurrentRequest", "");
            curRequest = gson.fromJson(json, Request.class);
        }
    }


}
