package com.example.hopinnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class LoginSignupActivity extends AppCompatActivity {
    public static final String TAG = "LoginSignupActivity";
    EditText nameEditText;
    EditText passwordEditText;
    EditText phoneNumberEditText;
    EditText emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_view);

        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        emailEditText = findViewById(R.id.email);
    }

    public void signup(View view) {
        final DatabaseAccessor databaseAccessor = new DatabaseAccessor();

        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        Rider rider = new Rider(name, password, phoneNumber, email);
        databaseAccessor.signupRider(rider);
    }
}
