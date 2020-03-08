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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapFragment mapFragment;
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private FloatingActionButton goOnline;

    private Rider rider;
    private Driver driver;
    private LatLng pickUpLoc,dropOffLoc;
    private String pickUpLocName, dropOffLocName;
    private Marker pickUpMarker, dropOffMarker;
    private FloatingActionButton driverMenuBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(DriverMapActivity.this);

        //TODO set rider, driver, car properly
        rider = new Rider();
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third", "12345678", true,12.0, null, car, null, null);
        //String email, String password, String name, String phoneNumber, boolean userType,double deposit,
        //                  Request curRequest, Car car, ArrayList<Request> availableRequest,
        //                  ArrayList<Trip> driverTripList

        goOnline = findViewById(R.id.onlineBtn);
        goOnline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        // a button listener
        driverMenuBtn = findViewById(R.id.driverMenuBtn);
        driverMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RiderMenuActivity.class);
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

    public void showAvailableRequest(){

    }
    public void pickUpRider(){

    }
    public void switchFragment(int caseId){
        FragmentTransaction t;
        switch(caseId){
            case R.layout.fragment_driver_pick_rider_up:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new pickUpAndCurrentRequest()).commit();
                break;
        }




    }
    public void callNumber(String phoneNumber){

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(DriverMapActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }


    /*
    *
    * public void switchFragment(int caseId){
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
    *
    *
    *
    *
    *
    *
    *
    * */
}
