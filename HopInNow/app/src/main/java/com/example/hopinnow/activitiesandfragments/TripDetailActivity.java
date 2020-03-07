package com.example.hopinnow.activitiesandfragments;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        driverName = findViewById(R.id.DriverName);
        riderName = findViewById(R.id.riderName);
        pickUpLocation = findViewById(R.id.pickupLoc);
        dropOffLocation = findViewById(R.id.dropoffLocation);


        //Intent intent = ;
        //trip = (Trip)intent.getSerializableExtra("trip");

        //driver = (Driver)intent.getSerializableExtra("driver");
        //position = intent.getIntExtra("trip_index",0);
        //rider = (Rider) intent.getSerializableExtra("rider");
       // driverName.setText(driver.getName());
        //riderName.setText(rider.getName());
        //pickUpLocation.setText(trip.getPickUpLocName());
       // dropOffLocation.setText(trip.getDropOffLocName());


    }
}
