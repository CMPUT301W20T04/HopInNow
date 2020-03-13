package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.google.type.LatLng;

public class TripDetailActivity extends AppCompatActivity implements DriverProfileStatusListener {
    private TextView driverName;
    private TextView riderName;
    public TextView pickUpLocation;
    private TextView dropOffLocation;
    private Driver driver;
    private Rider rider;
    private Trip trip;
    private LatLng pickUpLoc;
    private LatLng dropOffLoc;
    private int position;
    private TextView rating;
    private TextView cost;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        driverName = findViewById(R.id.DriverName);
        riderName = findViewById(R.id.riderName);
        pickUpLocation = findViewById(R.id.pickupLoc);
        dropOffLocation = findViewById(R.id.dropoffLocation);
        rating = findViewById(R.id.rating);
        cost = findViewById(R.id.cost);
        //get the key
        Intent intent = getIntent();
        int search_key = intent.getIntExtra("pos_key",0);

        trip = driver.getDriverTripList().get(search_key);

        //function that get the certain trip from the database
        driverDatabaseAccessor = new DriverDatabaseAccessor();
        driverDatabaseAccessor.getDriverProfile(this);
        //now set the view
        // FIXME
        //driverName.setText(trip.getDriverEmail().getName());
        //riderName.setText(trip.getRiderEmail().getName());
        pickUpLocation.setText(trip.getPickUpLoc().toString());
        dropOffLocation.setText(trip.getDropOffLoc().toString());
        rating.setText(trip.getRating().toString());
        cost.setText(trip.getCost().toString());










    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {

    }

    @Override
    public void onDriverProfileUpdateFailure() {

    }
}
