package com.example.hopinnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.database.DatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "RegisterActivity";
    // initialize FirebaseAuth:
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    // initialize Firebase Database reference:
    private DatabaseReference databaseReference;
    // UI components:
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText password2;
    private TextView passdiffwarn;
    private Switch driverSwitch;
    // progressBar for register wait:
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        this.currentUser = firebaseAuth.getCurrentUser();
        this.updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        this.firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase database reference
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        // link components
        this.name = findViewById(R.id.regNameEditText);
        this.email = findViewById(R.id.regEmailEditText);
        this.phoneNumber = findViewById(R.id.regPhoneNum);
        this.password = findViewById(R.id.regPassword);
        this.password2 = findViewById(R.id.reRegPassword2);
        this.passdiffwarn = findViewById(R.id.passdiffwarn);
        this.passdiffwarn.setVisibility(View.INVISIBLE);
        this.driverSwitch = findViewById(R.id.driver_rider_switch);
        // progress bar for registering wait:
        this.progressBar = new ProgressBar(this);
    }

    public void register(View v) {
        String password = this.password.getText().toString();
        String password2 = this.password2.getText().toString();
        if (password.compareTo(password2) != 0) {
            this.passdiffwarn.setVisibility(View.VISIBLE);
            return;
        } else {
            this.passdiffwarn.setVisibility(View.INVISIBLE);
        }
        final String name = this.name.getText().toString();
        final String email = this.email.getText().toString();
        final String phoneNumber = this.phoneNumber.getText().toString();
        final boolean isDriver = driverSwitch.isChecked();

        // show the progress bar:
        this.progressBar.setVisibility(View.VISIBLE);
        // create user in the database:
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            // save user information in the database:
                            User user;
                            if (isDriver) {
                                user = new Driver(email, null, name, phoneNumber, true,
                                        null, null, null, null);
                            } else {
                                user = new Rider(email, null, name, phoneNumber, false,
                                        null, null);
                            }
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            databaseReference.child(Objects.requireNonNull(firebaseUser).getUid()).setValue(user);
                            progressBar.setVisibility(View.GONE);
                            updateUI(firebaseUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Registration failed.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {  // if loggin in, go to login, then goto profile activity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
