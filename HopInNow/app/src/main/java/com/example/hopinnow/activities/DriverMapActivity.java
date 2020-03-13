package com.example.hopinnow.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapFragment mapFragment; // the map fragment
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private FloatingActionButton goOnline; //the go online button
    private Marker pickUpMarker;
    private LatLng pickUpLoc,dropOffLoc;
    private FloatingActionButton driverMenuBtn;//the driver menu button
    private int currentRequestPageCounter = 0;//switch the two pages of pickUp and DropOff fragment
    /**
     * set the visibility of goOnline button into invisible
     */
    public void setButtonInvisible(){
        goOnline.setVisibility(View.INVISIBLE);
    }

    /**
     * get the appear time of the fragment that display the current request
     * if it's the first time then display the pickup button
     * else display the emergency button and dropoff button
     * @return
     */
    public int getCurrentRequestPageCounter(){
        return this.currentRequestPageCounter;
    }

    /**
     * set new value for the current request and pickup/drop off page switcher
     * @param value
     */
    public void setCurrentRequestPageCounter(int value){
        this.currentRequestPageCounter = value;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(DriverMapActivity.this);

        goOnline = findViewById(R.id.onlineBtn);
        // set the go online button listener
        goOnline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // switch the fragment to driver request when click on the button.
                switchFragment(R.layout.fragment_driver_requests);
            }
        });
        // a button listener
        driverMenuBtn = findViewById(R.id.driverMenuBtn);
        driverMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start the menu activity when click on driverMenuButton
                Intent startIntent = new Intent(getApplicationContext(), DriverMenuActivity.class);
                startActivity(startIntent);
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


    /**
     * we have a frame layout in the rider and driver activity
     * so that the fragments on it can switch easily by calling
     * switchFragment method,
     * @param caseId
     */
    public void switchFragment(int caseId){
        FragmentTransaction t;
        switch(caseId){
            // change the fragment to the one that display the current
            // request and the pickup user button
            case R.layout.fragment_driver_pick_rider_up:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new pickUpAndCurrentRequest()).commit();
                break;
                //change the fragment to the one that display the available list.
            case R.layout.fragment_driver_requests:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RequestListFragment()).commit();
                break;
        }
    }

    /**
     * call number phoneNumber on the phone
     * @param phoneNumber
     */
    public void callNumber(String phoneNumber){
        // call number phoneNumber on the phone
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));
        if (ActivityCompat.checkSelfPermission(DriverMapActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    /**
     * set marker to map
     */
    public void setMapMarker(Marker m, LatLng latLng){
        if (m == null) {
            MarkerOptions opt = new MarkerOptions();
            opt.position(latLng);
            m = mMap.addMarker(opt);
        } else {
            m.setPosition(latLng);
        }
        adjustMapFocus();
    }

    /**
     * set the pick up location to new value
     * @param pickUpLoc
     */
    public void setPickUpLoc(LatLng pickUpLoc) {
        this.pickUpLoc = pickUpLoc;
    }

    /**
     * set the drop off location to new value
     * @param dropOffLoc
     */
    public void setDropOffLoc(LatLng dropOffLoc){
        this.dropOffLoc = dropOffLoc;
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
}
