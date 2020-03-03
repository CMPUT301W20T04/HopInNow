package com.example.hopinnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hopinnow.databasestatuslisteners.LoginStatusListener;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.databasestatuslisteners.UserProfileStatusListener;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements UserProfileStatusListener {
    // establish the TAG of this activity:
    public static final String TAG = "ProfileActivity";
    // declare database accessor:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI Components:
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private TextView userType;
    private Button editBtn;
    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // init the userDatabaseAccessor:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // check the login status:
        if (!this.userDatabaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // UI init:
        this.name = findViewById(R.id.proNameET);
        this.name.setEnabled(false);
        this.email = findViewById(R.id.proEmailET);
        this.email.setEnabled(false);
        this.phoneNumber = findViewById(R.id.proPhoneET);
        this.phoneNumber.setEnabled(false);
        this.userType = findViewById(R.id.proUserType);
        this.editBtn = findViewById(R.id.editProfileBtn);
        this.updateBtn = findViewById(R.id.proUpdateBtn);
        this.updateBtn.setVisibility(View.INVISIBLE);
        // retrieve the current user information
        this.userDatabaseAccessor.getUserProfile(this);
    }

    public void logout(View v) {
        this.userDatabaseAccessor.logoutUser();
        // go to the login activity again:
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void editProfile(View v) {

    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetreiveSuccess(User user) {
        // set all text fields according to the retreived user object:
        this.name.setText(Objects.requireNonNull(user).getName());
        this.email.setText(user.getEmail());
        this.phoneNumber.setText(user.getPhoneNumber());
        if (user.isUserType()) {    // if true, then the user is driver
            this.userType.setText(R.string.usertype_driver);
        } else {    // or else, the user is a rider
            this.userType.setText(R.string.usertype_rider);
        }
    }

    @Override
    public void onProfileRetreiveFailure() {

    }
}
