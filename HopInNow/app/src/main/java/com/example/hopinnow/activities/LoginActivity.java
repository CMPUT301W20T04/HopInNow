package com.example.hopinnow.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

/**
 * Author: Shuwei Wang
 * Version: 1.0.0
 * the first page of the app, user can choose to log in an existing account or register a new account
 */
public class LoginActivity extends AppCompatActivity implements LoginStatusListener,
        UserProfileStatusListener {
    // initialize Database helper:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI components:
    private EditText email;
    private EditText password;
    private TextView loginWarn;
    private Button loginButton;
    private TextView register;
    // alert progress dialog:
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initialize the userDatabaseAccessor to use the login function within it:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // Shway's progress bar:
        this.progressDialog = new ProgressDialog(LoginActivity.this);
        this.progressDialog.setContentView(R.layout.custom_progress_bar);
        // if user already logged in, go to the profile activity
        if (this.userDatabaseAccessor.isLoggedin()) {
            progressDialog.show();
            userDatabaseAccessor.getUserProfile(this);
        }
        // here, the database accessor is already initialized
        this.email = findViewById(R.id.loginEmailEditText);
        this.password = findViewById(R.id.loginPassword);
        this.loginWarn = findViewById(R.id.loginWarning);
        this.loginButton = findViewById(R.id.loginButton);
        this.register = findViewById(R.id.linkToRegister);
        // set the warning as invisible
        this.loginWarn.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loginButton.setOnClickListener(view -> {
            // checks if the inputs are valid:
            if (!verifyFields()) {
                loginWarn.setVisibility(View.VISIBLE);
                return;
            } else {
                loginWarn.setVisibility(View.INVISIBLE);
            }
            // alert progress dialog:


            progressDialog.show();
            // access database:
            String emailData = email.getText().toString();
            String passwordData = password.getText().toString();
            User user = new User();
            user.setEmail(emailData);
            user.setPassword(passwordData);
            // log in the user
            userDatabaseAccessor.loginUser(user, LoginActivity.this);
        });
        this.register.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            //finish();
        });
    }

    private boolean verifyFields() {
        // get the strings
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        return emailData.compareTo("") != 0 && passwordData.compareTo("") != 0;
    }

    @Override
    public void onLoginSuccess() {
        // go view the map:
        this.userDatabaseAccessor.getUserProfile( this);
        Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFailure() {
        this.progressDialog.dismiss();
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Login Failed, try again later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        Intent intent;
        if (user.isUserType()) {    // true is driver
            intent = new Intent(getApplicationContext(), DriverMapActivity.class);
        } else {    // false is rider
            intent = new Intent(getApplicationContext(), RiderMapActivity.class);
        }
        startActivity(intent);
        if (!LoginActivity.this.isFinishing() && progressDialog!=null) {
            this.progressDialog.dismiss();
        }
        finish();
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

    @Override
    public void onBackPressed() {

    }
}
