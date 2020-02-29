package com.example.hopinnow.Activities.SignupAndLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hopinnow.Database.DatabaseAccessor;
import com.example.hopinnow.R;
import com.example.hopinnow.entities.Rider;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

        Rider rider = new Rider(name, password, phoneNumber, email,null,null);
        databaseAccessor.registerRider(getApplicationContext(), RiderProfileActivity.class, rider);
    }
}
