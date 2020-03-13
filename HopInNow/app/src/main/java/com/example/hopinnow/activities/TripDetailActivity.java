package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.type.LatLng;

public class TripDetailActivity extends AppCompatActivity implements DriverProfileStatusListener, UserProfileStatusListener, RiderProfileStatusListener {
    private TextView driverEmail;
    private TextView riderEmail;
    public TextView pickUpLocation;
    private TextView dropOffLocation;
    private Driver driver;
    private Rider rider;
    private Trip trip;
    private TextView rating;
    private TextView cost;
    private boolean type;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    private UserDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        driverEmail = findViewById(R.id.DriverEmail);
        riderEmail = findViewById(R.id.riderEmail);
        pickUpLocation = findViewById(R.id.pickupLoc);
        dropOffLocation = findViewById(R.id.dropoffLocation);
        rating = findViewById(R.id.rating);
        cost = findViewById(R.id.cost);
        //get the key
        Intent intent = getIntent();
        int search_key = intent.getIntExtra("pos_key",0);

        userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getUserProfile(this);
        if(type){
            //driver
            trip = driver.getDriverTripList().get(search_key);
            //function that get the certain trip from the database
            driverDatabaseAccessor = new DriverDatabaseAccessor();
            driverDatabaseAccessor.getDriverProfile(this);
        }
        else{
            trip = rider.getRiderTripList().get(search_key);
            riderDatabaseAccessor = new RiderDatabaseAccessor();
            riderDatabaseAccessor.getRiderProfile(this);
        }




        //now set the view
        // FIXME
        driverEmail.setText("Driver Email: "+trip.getDriverEmail());
        riderEmail.setText("Rider Email: "+trip.getRiderEmail());
        pickUpLocation.setText("Pick Up Location: "+trip.getPickUpLoc().toString());
        dropOffLocation.setText("Drop Off Location: "+trip.getDropOffLoc().toString());
        rating.setText("Rating: "+trip.getRating().toString());
        cost.setText("Cost: "+trip.getCost().toString());










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

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.type = user.isUserType();
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
}
