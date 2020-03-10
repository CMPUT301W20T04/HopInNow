package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.google.type.LatLng;

public class TripDetailActivity extends AppCompatActivity {
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
        //String search_key = intent.getExtras().getString("key");


        //function that get the certain trip from the database

        //now set the view
        // FIXME
        //driverName.setText(trip.getDriverEmail().getName());
        //riderName.setText(trip.getRiderEmail().getName());
        pickUpLocation.setText(trip.getPickUpLoc().toString());
        dropOffLocation.setText(trip.getDropOffLoc().toString());
        rating.setText(trip.getRating().toString());
        cost.setText(trip.getCost().toString());










    }
}
