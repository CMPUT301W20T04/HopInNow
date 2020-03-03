package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.databasestatuslisteners.LoginStatusListener;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.User;

public class LoginActivity extends AppCompatActivity implements LoginStatusListener {
    // establish the TAG of this activity:
    public static final String TAG = "LoginActivity";
    // initialize Database helper:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI components:
    private EditText email;
    private EditText password;
    private TextView loginWarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initialize the userDatabaseAccessor to use the login function within it:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // if user already logged in, go to the profile activity
        if (this.userDatabaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        // here, the database accessor is already initialized
        this.email = findViewById(R.id.loginEmailEditText);
        this.password = findViewById(R.id.loginPassword);
        this.loginWarn = findViewById(R.id.loginWarning);
        // set the warning as invisible
        this.loginWarn.setVisibility(View.INVISIBLE);
    }

    private boolean verifyFields() {
        // get the strings
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        return emailData.compareTo("") != 0 && passwordData.compareTo("") != 0;
    }

    public void login(View v) {
        // checks if the inputs are valid:
        if (!this.verifyFields()) {
            this.loginWarn.setVisibility(View.VISIBLE);
        } else {
            this.loginWarn.setVisibility(View.INVISIBLE);
        }
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        User user = new User();
        user.setEmail(emailData);
        user.setPassword(passwordData);
        // log in the user
        this.userDatabaseAccessor.loginUser(user, this);
    }

    public void toRegister(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginSuccess() {
        // go view the user profile:
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {
        // display the login failure massage:
        Toast.makeText(getApplicationContext(),
                "Login Failed, try again later.", Toast.LENGTH_SHORT).show();
    }
}
