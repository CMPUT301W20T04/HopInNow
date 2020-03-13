package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hopinnow.R;

/**
 * Author: Peter Zhou
 * Version: 1.0.0
 * This class helps show the rider's menu when clicking the menu button on the top left cornor
 */
public class RiderMenuActivity extends AppCompatActivity {


    private Button riderMyProfileBtn, riderChatBtn, riderMyTripsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_menu);


        riderMyProfileBtn = (Button) findViewById(R.id.riderMyProfile);
        riderChatBtn = (Button) findViewById(R.id.riderChat);
        riderMyTripsBtn = (Button) findViewById(R.id.riderMyTrips);


        riderMyProfileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        riderMyTripsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
                startActivity(intent);
            }
        });


    }
}
