package com.example.hopinnow.ActivitiesAndFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.Database.DatabaseAccessor;
import com.example.hopinnow.R;

public class ProfileFragment extends AppCompatActivity {
    private int modeSwitch = 0;//0 for rider and 1 for driver
    private EditText nameEt;
    private EditText phoneNumberEt;
    private EditText emailEt;
    private EditText carMake;
    private EditText carModel;
    private EditText carPlate;
    private EditText carColor;
    private EditText driverRating;
    public Button updateBtn;
    public EditText password;//add password to uml
    public ProfileFragment(String name, String password, String phoneNumber, String email){
        this.nameEt.setText(name);
        this.password.setText(password);
        this.phoneNumberEt.setText(phoneNumber);
        this.emailEt.setText(email);

    }
    public ProfileFragment(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        phoneNumberEt = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
    }

    public void setProfileMode(int mode) {
        this.modeSwitch = mode;
    }
    /*
    public void getProfile(){
        final DatabaseAccessor databaseAccessor = new DatabaseAccessor();
        FirebaseUser rider =  databaseAccessor.getUserProfile(getApplicationContext());
        nameEt.setText(rider.getDisplayName());
        phoneNumberEt.setText(rider.getPhoneNumber());
        emailEt.setText(rider.getEmail());
        //need password
    }*/

    public void updateProfile(View view){
        //save the new profile to database
        final DatabaseAccessor databaseAccessor = new DatabaseAccessor();

    }


}