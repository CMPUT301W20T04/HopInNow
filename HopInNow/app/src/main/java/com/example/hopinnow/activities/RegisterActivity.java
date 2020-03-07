package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.statuslisteners.RegisterStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;

public class RegisterActivity extends AppCompatActivity implements LoginStatusListener, RegisterStatusListener, UserProfileStatusListener {
    // establish the TAG of this activity:
    public static final String TAG = "RegisterActivity";
    // current user information:
    User user;
    // Database methods:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI components:
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private EditText password;
    private EditText password2;
    private TextView passdiffwarn;
    private Switch driverSwitch;
    // alert progress dialog:
    private ProgressbarDialog progressbarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // init the userDatabaseAccessor:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // if user already logged in, go to the profile activity
        if (this.userDatabaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        // link components
        this.name = findViewById(R.id.regNameEditText);
        this.email = findViewById(R.id.regEmailEditText);
        this.phoneNumber = findViewById(R.id.regPhoneNum);
        this.password = findViewById(R.id.regPassword);
        this.password2 = findViewById(R.id.reRegPassword2);
        this.passdiffwarn = findViewById(R.id.passdiffwarn);
        this.passdiffwarn.setVisibility(View.INVISIBLE);
        this.driverSwitch = findViewById(R.id.driver_rider_switch);
    }

    private boolean verifyFields() {
        // initialize the user object to store:
        final String password = this.password.getText().toString();
        String password2 = this.password2.getText().toString();
        // the length of the password must be greater than 6!
        // FIXME
        if (password.compareTo(password2) != 0) {
            return false;   // no input problem detected
        } else return password.length() >= 6 && password2.length() >= 6;
    }

    public void register(View v) {
        if (!verifyFields()) {
            this.passdiffwarn.setVisibility(View.VISIBLE);
            return;
        } else {
            this.passdiffwarn.setVisibility(View.INVISIBLE);
        }
        // initialize the user object to store:
        String password = this.password.getText().toString();
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();
        boolean isDriver = driverSwitch.isChecked();
        // save user information in the database:
        if (isDriver) { // the user is a driver
            this.user = new Driver(email, password, name, phoneNumber, true,
                    null, null, null, null);
        } else {    // the user is a rider
            this.user = new Rider(email, password, name, phoneNumber, false,
                    null, null);
        }
        // alert progress dialog:
        ViewGroup viewGroup = findViewById(R.id.activity_register);
        progressbarDialog = new ProgressbarDialog(RegisterActivity.this, viewGroup);
        progressbarDialog.startProgressbarDialog();
        // create user in the database:
        this.userDatabaseAccessor.registerUser(this.user, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onLoginSuccess() {
        this.progressbarDialog.dismissDialog();
        // go view the user profile:
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {
        this.progressbarDialog.dismissDialog();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Login Failed, try again later.", Toast.LENGTH_SHORT).show();
        // go view the user profile:
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getApplicationContext(),
                "User created, logging in the user...", Toast.LENGTH_SHORT).show();
        this.userDatabaseAccessor.createUserProfile(this.user, this);
        this.userDatabaseAccessor.loginUser(this.user, this);
    }

    @Override
    public void onRegisterFailure() {
        this.progressbarDialog.dismissDialog();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Registration Failed, try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileRetrieveSuccess(User user) {

    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }
}
