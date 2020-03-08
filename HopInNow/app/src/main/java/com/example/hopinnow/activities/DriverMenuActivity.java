package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DriverMenuActivity extends AppCompatActivity implements UserProfileStatusListener {

    private FloatingActionButton offlineBtn;
    private Button driverMyProfileBtn, driverMyTripsBtn, vehicleInfoBtn;
    private TextView driverMenuTextView;
    private UserDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_menu);

        driverMyProfileBtn = (Button) findViewById(R.id.driverMyProfileBtn);
        driverMyTripsBtn = (Button) findViewById(R.id.driverMyTripsBtn);
        vehicleInfoBtn = (Button) findViewById(R.id.vehicleInfoBtn);
        offlineBtn = (FloatingActionButton) findViewById(R.id.offlineBtn);
        this.userDatabaseAccessor = new UserDatabaseAccessor();

        driverMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        driverMyTripsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
                startActivity(intent);
            }
        });

        vehicleInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDatabaseAccessor.getUserProfile(DriverMenuActivity.this);
            }
        });

        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        Intent intent = new Intent(getApplicationContext(),  VehicleViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DriverObject", (Driver)user);
        intent.putExtras(bundle);
        startActivity(intent);
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
}
