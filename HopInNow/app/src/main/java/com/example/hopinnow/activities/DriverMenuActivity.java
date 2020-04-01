package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Author: Peter Zhou
 * This class helps show the driver's menu when clicking the menu button on the top left cornor
 */
public class DriverMenuActivity extends AppCompatActivity implements DriverProfileStatusListener {
    public static final String TAG = "DriverMenuActivity";
    private TextView driverMenuTextView;
    private String rating;
    private DriverDatabaseAccessor userDatabaseAccessor;

    /**
     * create the activity
     * @param savedInstanceState
     *      this is the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_menu);
        //initialize buttons
        Button driverMyProfileBtn = findViewById(R.id.driverMyProfileBtn);
        Button driverMyTripsBtn = findViewById(R.id.driverMyTripsBtn);
        Button vehicleInfoBtn = findViewById(R.id.vehicleInfoBtn);
        FloatingActionButton offlineBtn = findViewById(R.id.offlineBtn);
        this.userDatabaseAccessor = new DriverDatabaseAccessor();

        driverMyProfileBtn.setOnClickListener(v -> {
            // set driver check my profile button listener
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("DriverRating", rating);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        driverMyTripsBtn.setOnClickListener(v -> {
            //set driver check trip button listener
            Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
            startActivity(intent);
        });
        //
        vehicleInfoBtn.setOnClickListener(v -> {
            Log.d(TAG, "Car info btn clicked!");
            userDatabaseAccessor.getDriverProfile(DriverMenuActivity.this);
        });
        // set driver offline when click on the offline button
        offlineBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DriverMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

    }

    /**
     * when retrieve the driver profile successful,
     * open vehicle view activity to display the car information
     * @param driver
     *      the driver object just retrieved
     */
    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        // when retrieve the driver profile successful,
        // open vehicle view activity to display the car information
        if (driver.getRating()!=null){
            this.rating = driver.getRating().toString();
        } else {
            this.rating = null;
        }

        Log.v(TAG, "Driver info retrieved!");
        Intent intent = new Intent(getApplicationContext(),  VehicleViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DriverObject", driver);
        intent.putExtras(bundle);
        startActivity(intent);
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
