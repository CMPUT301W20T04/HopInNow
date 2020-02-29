package com.example.hopinnow;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    public Button returnBtn;
    public EditText password;//add password to uml
    public ProfileFragment(String name, String password, String phoneNumber, String email){


    }


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

    public void getProfile(Rider rider){
        final DatabaseAccessor databaseAccessor = new DatabaseAccessor();

    }

    public void updateProfile(View view){
        //save the new profile to database

    }

    public void returnButton(View view){
        //back to the menu

        //getActivity().getFragmentManager().popBackStack();
    }
}