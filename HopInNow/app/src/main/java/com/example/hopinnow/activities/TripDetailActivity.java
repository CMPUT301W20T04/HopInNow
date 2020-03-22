package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderObjectRetrieveListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.type.LatLng;

/**
 * Author: Qianxi Li
 * Version: 1.0.0
 * Show the detail information of historical trip after clicking on one row in trip history
 */
public class TripDetailActivity extends AppCompatActivity implements DriverProfileStatusListener,
        UserProfileStatusListener, RiderProfileStatusListener, DriverObjectRetreieveListener, RiderObjectRetrieveListener {
    private TextView driverEmail;
    private TextView riderEmail;
    private TextView pickUpLocation;
    private TextView dropOffLocation;
    private TextView otherName;
    private TextView otherPhone;
    private TextView otherRating;
    private Driver driver;
    private Rider rider;
    private Driver otherDriver;
    private Rider otherRider;
    private Trip trip;
    private TextView rating;
    private TextView cost;
    private boolean type;
    private int search_key;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    private UserDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        driverEmail = findViewById(R.id.DriverEmail);
        riderEmail = findViewById(R.id.riderEmail);
        pickUpLocation = findViewById(R.id.pickUpLocation);
        dropOffLocation = findViewById(R.id.dropoffLocation);
        rating = findViewById(R.id.rating2);
        cost = findViewById(R.id.cost);
        otherName = findViewById(R.id.otherName);
        otherPhone = findViewById(R.id.otherPhoneNumber);
        otherRating = findViewById(R.id.otherRating);
        otherRating.setVisibility(View.INVISIBLE);

        //get the key
        Intent intent = getIntent();
        search_key = intent.getIntExtra("pos_key",0);

        userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getUserProfile(this);

    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.driver = driver;
        trip = driver.getDriverTripList().get(search_key);
        driverEmail.setText("Driver Email: "+trip.getDriverEmail());
        riderEmail.setText("Rider Email: "+trip.getRiderEmail());
        pickUpLocation.setText(String.format("Pick Up Location: %s",trip.getPickUpLocName()));
        dropOffLocation.setText(String.format("Drop Off Location: %s",trip.getDropOffLocName()));
        rating.setText("Rating: "+trip.getRating().toString());
        cost.setText("Revenue: "+trip.getCost().toString());
        System.out.println(trip.getRiderEmail());
        riderDatabaseAccessor.getRiderObject(trip.getRiderEmail(),this);

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

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {

        this.type = user.isUserType();
        driverDatabaseAccessor = new DriverDatabaseAccessor();
        riderDatabaseAccessor = new RiderDatabaseAccessor();
        if(type){
            //driver
            //function that get the certain trip from the database
            driverDatabaseAccessor.getDriverProfile(this);
            ((TextView)findViewById(R.id.textView8)).setText("Rider Information");
        }
        else{
            otherRating.setVisibility(View.INVISIBLE);
            riderDatabaseAccessor.getRiderProfile(this);
        }



    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onRiderProfileRetrieveSuccess(Rider rider) {
        this.rider = rider;
        trip = this.rider.getRiderTripList().get(search_key);
        driverEmail.setText("Driver Email: "+trip.getDriverEmail());
        riderEmail.setText("Rider Email: "+trip.getRiderEmail());
        pickUpLocation.setText(String.format("Pick Up Location: %s",trip.getPickUpLocName()));
        dropOffLocation.setText(String.format("Drop Off Location: %s",trip.getDropOffLocName()));
        rating.setText("Rating: "+trip.getRating().toString());
        cost.setText("Cost: "+trip.getCost().toString());
        driverDatabaseAccessor.getDriverObject(trip.getDriverEmail(),this);

    }

    @Override
    public void onRiderProfileRetrieveFailure() {

    }

    @Override
    public void onRiderProfileUpdateSuccess(Rider rider) {

    }

    @Override
    public void onRiderProfileUpdateFailure() {

    }

    @Override
    public void onDriverObjRetrieveSuccess(Driver driver) {
        this.otherDriver = driver;
        System.out.println("123 "+this.otherDriver.getEmail());
        otherName.setText(this.otherDriver.getName());
        otherRating.setText(this.otherDriver.getRating().toString());
        otherPhone.setText(this.otherDriver.getPhoneNumber());
    }

    @Override
    public void onDriverObjRetrieveFailure() {

    }

    @Override
    public void onRiderObjRetrieveSuccess(Rider rider) {
        this.otherRider = rider;
        System.out.println("123 "+this.otherRider.getEmail());
        otherName.setText(this.otherRider.getName());
        otherPhone.setText(this.otherRider.getPhoneNumber());
    }

    @Override
    public void onRiderObjRetrieveFailure() {

    }
}
