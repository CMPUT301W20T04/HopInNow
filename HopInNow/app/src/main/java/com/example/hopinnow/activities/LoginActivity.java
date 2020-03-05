package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;

public class LoginActivity extends AppCompatActivity implements LoginStatusListener {
    // establish the TAG of this activity:
    public static final String TAG = "LoginActivity";
    // initialize Database helper:
    private UserDatabaseAccessor userDatabaseAccessor;
    // UI components:
    private EditText email;
    private EditText password;
    private TextView loginWarn;
    private Button loginButton;
    private TextView register;
    // alert progress dialog:
    private ProgressbarDialog progressbarDialog;
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
        this.loginButton = findViewById(R.id.loginButton);
        this.register = findViewById(R.id.linkToRegister);
        // set the warning as invisible
        this.loginWarn.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checks if the inputs are valid:
                if (!verifyFields()) {
                    loginWarn.setVisibility(View.VISIBLE);
                    return;
                } else {
                    loginWarn.setVisibility(View.INVISIBLE);
                }
                // alert progress dialog:
                ViewGroup viewGroup = findViewById(R.id.activity_login);
                progressbarDialog = new ProgressbarDialog(LoginActivity.this, viewGroup);
                progressbarDialog.startProgressbarDialog();
                // access database:
                String emailData = email.getText().toString();
                String passwordData = password.getText().toString();
                User user = new User();
                user.setEmail(emailData);
                user.setPassword(passwordData);
                // log in the user
                userDatabaseAccessor.loginUser(user, LoginActivity.this);
            }
        });
        this.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
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
        // go view the user profile:
        this.progressbarDialog.dismissDialog();
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
    }
}
