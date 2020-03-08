package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DriverMenuActivity extends AppCompatActivity {

    private FloatingActionButton offlineBtn;
    private Button driverMyProfileBtn, driverMyTripsBtn, vehicleInfoBtn;
    private TextView driverMenuTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_menu);

        driverMyProfileBtn = (Button) findViewById(R.id.driverMyProfileBtn);
        driverMyTripsBtn = (Button) findViewById(R.id.driverMyTripsBtn);
        vehicleInfoBtn = (Button) findViewById(R.id.vehicleInfoBtn);
        offlineBtn = (FloatingActionButton) findViewById(R.id.offlineBtn);


        driverMyProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverProfileActivity.class);
                startActivity(intent);
            }
        });

        driverMyTripsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverTripListActivity.class);
                startActivity(intent);
            }
        });

        vehicleInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VehicleInfoActivity.class);
                startActivity(intent);
            }
        });

        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
