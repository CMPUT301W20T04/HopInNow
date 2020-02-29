package com.example.hopinnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    private Button riderMyProfileBtn, riderChatBtn, riderMyTripsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        riderMyProfileBtn = (Button) findViewById(R.id.riderMyProfile);
        riderChatBtn = (Button) findViewById(R.id.riderChat);
        riderMyTripsBtn = (Button) findViewById(R.id.riderMyTrips);

        riderMyProfileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });




    }
}
