package com.example.hopinnow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RiderMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapFragment mapFragment;
    /**change to current location later on pickUpLoc*/
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private Button addRequest;

    private LatLng pickUpLoc,dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Request curRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(RiderMapActivity.this);

        //initialize autocomplete fragments
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "Your api key");
        }
        setupAutoCompleteFragment();


        addRequest = findViewById(R.id.add_request_button);
        addRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 LatLng pickUpLoc = null;
                 LatLng dropOffLoc = null;
                 Driver driver = null;
                 Rider rider = null;
                 Date dateTime = Calendar.getInstance().getTime();
                 Car car = null;
                 EstimateFare fare = new EstimateFare();
                 Double estimatedFare = fare.estimateFare(pickUpLoc,dropOffLoc,dateTime);
                 //set current Request
                 curRequest = new Request(driver,rider, pickUpLoc, dropOffLoc, dateTime,car, estimatedFare);
                 */


                /**pop up dialog, show current request waiting to be accepted*/


            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton, 8.5f));
        mMap.addMarker(new MarkerOptions()
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
                    pickUpLocName = place.getName();
                    pickUpLoc = place.getLatLng();
                    mMap.addMarker(new MarkerOptions()
                                    .position(pickUpLoc)
                                    .title("Pick Up Location"));
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
                    dropOffLocName = place.getName();
                    dropOffLoc = place.getLatLng();
                    mMap.addMarker(new MarkerOptions()
                            .position(dropOffLoc)
                            .title("Drop Off Location"));
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
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            //mMap.clear();
        }
    }


}
