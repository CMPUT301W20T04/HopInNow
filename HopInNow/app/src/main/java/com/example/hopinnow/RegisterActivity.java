package com.example.hopinnow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "RegisterActivity";
    DatabaseAccessor databaseAccessor;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // init the databaseAccessor:
        this.databaseAccessor = new DatabaseAccessor();
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
        this.progressBar = findViewById(R.id.regProgressBar);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // if user already logged in, go to the profile activity
        if (this.databaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void register(View v) {
        // set the progress bar:
        this.progressBar.setVisibility(View.VISIBLE);
        // initialize the user object to store:
        final String password = this.password.getText().toString();
        String password2 = this.password2.getText().toString();
        // the length of the password must be greater than 6!
        // FIXME
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

        // save user information in the database:
        User user;
        if (isDriver) { // the user is a driver
            user = new Driver(email, password, name, phoneNumber, true,
                    null, null, null, null);
        } else {    // the user is a rider
            user = new Rider(email, password, name, phoneNumber, false,
                    null, null);
        }
        // create user in the database:
        this.databaseAccessor.registerUser(TAG, user);
        this.databaseAccessor.waitAndAct(this,
                ProfileActivity.class, "User");
    }
}
