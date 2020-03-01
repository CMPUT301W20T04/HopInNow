package com.example.hopinnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "ProfileActivity";
    // initialize FirebaseAuth:
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        this.updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_profile);
        // firebase init:
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        // UI init:
        this.name = findViewById(R.id.proNameET);
        this.name.setText(this.firebaseUser.getDisplayName());
        this.name.setEnabled(false);
        this.email = findViewById(R.id.proEmailET);
        this.email.setText(this.firebaseUser.getEmail());
        this.email.setEnabled(false);
        this.phoneNumber = findViewById(R.id.proPhoneET);
        this.phoneNumber.setText(this.firebaseUser.getPhoneNumber());
        this.phoneNumber.setEnabled(false);
        this.userType = findViewById(R.id.proUserType);

        this.editBtn = findViewById(R.id.editProfileBtn);
        this.updateBtn = findViewById(R.id.proUpdateBtn);
        this.updateBtn.setVisibility(View.INVISIBLE);
    }

    public void editProfile(View v) {

    }

    public void logout(View v) {
        this.firebaseAuth.signOut();
        this.updateUI(firebaseAuth.getCurrentUser());
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
