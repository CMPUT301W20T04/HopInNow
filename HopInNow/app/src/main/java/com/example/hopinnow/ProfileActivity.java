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
import android.widget.Toast;

import com.example.hopinnow.database.DatabaseAccessor;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "ProfileActivity";
    /*// initialize FirebaseAuth:
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;*/
    DatabaseAccessor databaseAccessor;
    // UI Components:
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private TextView userType;
    private Button editBtn;
    private Button updateBtn;
    @Override
    protected void onStart() {
        super.onStart();
        this.databaseAccessor = new DatabaseAccessor();
        if (!this.databaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);
        /*// firebase init:
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();*/
        // retrieve the current user information
        User user = this.databaseAccessor.getUserProfile(TAG);
        // UI init:
        this.name = findViewById(R.id.proNameET);
        this.name.setText(Objects.requireNonNull(user).getName());
        this.name.setEnabled(false);
        this.email = findViewById(R.id.proEmailET);
        this.email.setText(user.getEmail());
        this.email.setEnabled(false);
        this.phoneNumber = findViewById(R.id.proPhoneET);
        this.phoneNumber.setText(user.getPhoneNumber());
        this.phoneNumber.setEnabled(false);
        this.userType = findViewById(R.id.proUserType);
        if (user.isUserType()) {    // if true, then the user is driver
            this.userType.setText(R.string.usertype_driver);
        } else {    // or else, the user is a rider
            this.userType.setText(R.string.usertype_rider);
        }
        this.editBtn = findViewById(R.id.editProfileBtn);
        this.updateBtn = findViewById(R.id.proUpdateBtn);
        this.updateBtn.setVisibility(View.INVISIBLE);
    }

    public void editProfile(View v) {

    }
}
