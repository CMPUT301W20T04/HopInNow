package com.example.hopinnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.database.DatabaseAccessor;
import com.example.hopinnow.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "LoginActivity";
    /*// initialize FirebaseAuth:
    private FirebaseAuth firebaseAuth;*/
    // initialize Database helper:
    DatabaseAccessor databaseAccessor;
    // UI components:
    private EditText email;
    private EditText password;
    private TextView loginWarn;
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        this.databaseAccessor = new DatabaseAccessor();
        // if user already logged in, go to the profile activity
        if (this.databaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // here, the database accessor is already initialized
        this.email = findViewById(R.id.loginEmailEditText);
        this.password = findViewById(R.id.loginPassword);
        this.loginWarn = findViewById(R.id.loginWarning);
        // set the warning as invisible
        this.loginWarn.setVisibility(View.INVISIBLE);
    }

    public void login(View v) {
        // get the strings
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        if (emailData.compareTo("") == 0 || passwordData.compareTo("") == 0) {
            this.loginWarn.setVisibility(View.VISIBLE);
            return;
        } else {
            this.loginWarn.setVisibility(View.INVISIBLE);
        }
        User user = new User();
        user.setEmail(emailData);
        user.setPassword(passwordData);
        // log in the user
        this.databaseAccessor.loginUser(TAG, user);
        // check if logged in, go to user profile activity:
        if (this.databaseAccessor.isLoggedin()) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void toRegister(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
