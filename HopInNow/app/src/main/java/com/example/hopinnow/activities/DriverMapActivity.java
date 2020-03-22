package com.example.hopinnow.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * This is the main page for driver where is shows the map, online and menu button
 */
public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{
    private GoogleMap mMap;
    MapFragment mapFragment;
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private FloatingActionButton goOnline;
    private Location current;
    private Rider rider;
    private Driver driver;
    private Marker pickUpMarker, dropOffMarker;
    private LatLng pickUpLoc,dropOffLoc;
    private FloatingActionButton driverMenuBtn;
    private LatLng myPosition;
    private int currentRequestPageCounter = 0;
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
    public void setCurrentRequestPageCounter(int value){
        this.currentRequestPageCounter = value;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(DriverMapActivity.this);

        rider = new Rider();
        goOnline = findViewById(R.id.onlineBtn);
        goOnline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.map).getLayoutParams().height = getBaseContext().getResources().getDisplayMetrics().heightPixels-800;
                findViewById(R.id.onlineButtonText).setVisibility(View.INVISIBLE);
                switchFragment(R.layout.fragment_driver_requests);
            }
        });
        // a button listener
        driverMenuBtn = findViewById(R.id.driverMenuBtn);
        driverMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), DriverMenuActivity.class);
                startActivity(startIntent);
            }
        });
        if ((ActivityCompat.checkSelfPermission(DriverMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(DriverMapActivity.this,
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edmonton, 8.5f));
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
                t.replace(R.id.fragment_place, new PickUpAndCurrentRequest()).commit();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
                //change the fragment to the one that display the available list.
            case R.layout.fragment_driver_requests:
                t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.fragment_place, new RequestListFragment()).commit();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    public void clearMap(){
        mMap.clear();
    }
    public void updateBothMarker(){
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

    }
    public void setBothMarker(LatLng pickUpLoc, LatLng dropOffLoc){
        pickUpMarker.setVisible(true);
        pickUpMarker.setPosition(pickUpLoc);
        dropOffMarker.setVisible(true);
        dropOffMarker.setPosition(dropOffLoc);
        adjustMapFocus();
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
    public void setPickUpLoc(LatLng pickUpLoc) {
        this.pickUpLoc = pickUpLoc;
    }
    public void setDropOffLoc(LatLng dropOffLoc){
        this.dropOffLoc = dropOffLoc;
    }
    public Location getCurrentLoc(){
        return current;
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
    public void onBackPressed() {
    }

    @Override
    public void onLocationChanged(Location location) {
        this.current = location;
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
