package com.example.hopinnow.ActivitiesAndFragments.SignupAndLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hopinnow.Database.DatabaseAccessor;
import com.example.hopinnow.R;
import com.google.firebase.auth.FirebaseUser;

public class RiderProfileActivity extends AppCompatActivity {
    private DatabaseAccessor da;
    private TextView riderName;
    private TextView riderEmail;
    private TextView riderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);
        // link UI components:
        riderName = findViewById(R.id.riderName);
        riderEmail = findViewById(R.id.riderEmail);
        riderNumber = findViewById(R.id.phoneNumber);
        // get database instance:
        da = new DatabaseAccessor();
        FirebaseUser firebaseUser = da.getUserProfile();
        riderName.setText(firebaseUser.getDisplayName());
        riderEmail.setText(firebaseUser.getEmail());
        //riderNumber.setText(firebaseUser.getPhoneNumber());
    }

    public void signout(View v) {
        da.logoutUser(getApplicationContext(), SignupActivity.class);
    }
}
