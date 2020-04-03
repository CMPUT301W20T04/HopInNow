package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;

/**
 * Author: Qianxi Li
 * Co-author and editor: Shway Wang
 * Version: 1.0.1
 * display the historical trips of the user inside my trip in menu
 */
public class TripListActivity extends AppCompatActivity implements DriverProfileStatusListener,
        UserProfileStatusListener, RiderProfileStatusListener {
    private static final String TAG = "TripListActivity";
    private ListView tripList;
    private ArrayAdapter<Trip> tripAdapter;
    private ArrayList<Trip> tripDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripList = findViewById(R.id.trip_list);
        UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getUserProfile(this);
    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        //remember to change the uml
        this.tripDataList = driver.getDriverTripList();
        // DEBUG:
        if (this.tripDataList != null) {
            for (Trip trip : this.tripDataList) {
                Log.v(TAG, trip.getDropOffLocName());
            }
        }
        if (this.tripDataList != null) {
            tripAdapter = new CustomTripList(this.getApplicationContext(), this.tripDataList);
            tripList.setAdapter(tripAdapter);
            tripAdapter.notifyDataSetChanged();
            tripList.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getApplicationContext(),TripDetailActivity.class);
                //pass in a key
                intent.putExtra("pos_key", position);
                startActivity(intent);
            });
        }
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
        boolean usertype = user.isUserType();
        if(usertype){
            //is a driver
            DriverDatabaseAccessor driverDatabaseAccessor = new DriverDatabaseAccessor();
            driverDatabaseAccessor.getDriverProfile(this);
        }else{
            //is a rider
            RiderDatabaseAccessor riderDatabaseAccessor = new RiderDatabaseAccessor();
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
        this.tripDataList = rider.getRiderTripList();
        if (this.tripDataList != null) {
            tripAdapter = new CustomTripList(this,tripDataList);
            tripList.setAdapter(tripAdapter);
            tripAdapter.notifyDataSetChanged();
            tripList.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getApplicationContext(),TripDetailActivity.class);
                //pass in a key
                intent.putExtra("pos_key", position);
                startActivity(intent);
            });
        }
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