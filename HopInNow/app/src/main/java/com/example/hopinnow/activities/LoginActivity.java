package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.database.RequestDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.helperclasses.LatLong;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements LoginStatusListener,
        UserProfileStatusListener, AvailRequestListListener {
    // establish the TAG of this activity:
    public static final String TAG = "LoginActivity";
    // initialize Database helper:
    // FIXME
    private RequestDatabaseAccessor userDatabaseAccessor;
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
        this.userDatabaseAccessor = new RequestDatabaseAccessor();
        // if user already logged in, go to the profile activity
//        if (this.userDatabaseAccessor.isLoggedin()) {
//            Intent intent = new Intent(getApplicationContext(), RiderMapActivity.class);
//            startActivity(intent);
//            finish();
//        }
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
                // FIXME
                Request request = new Request();
                request.setEstimatedFare(3.0);
                request.setCar(new Car("QQ", "V10", "red", "dosj39"));
                request.setRiderEmail("test@email.com");
                request.setPickUpLoc(new LatLong(30, 30));
                request.setPickUpLocName("Test Pickup");
                request.setDropOffLoc(new LatLong(90, 90));
                request.setDropOffLocName("Test dropoff");
                request.setPickUpDateTime(new Date());
                userDatabaseAccessor.addRequest(request, LoginActivity.this);
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
        // go view the map:
        this.userDatabaseAccessor.getUserProfile( this);
    }

    @Override
    public void onLoginFailure() {
        this.progressbarDialog.dismissDialog();
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
        this.progressbarDialog.dismissDialog();
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

    @Override
    public void onRequestAddedSuccess() {
        Toast.makeText(getApplicationContext(), "Request added successfully!", Toast.LENGTH_LONG).show();
        this.userDatabaseAccessor.getAllRequest(this);
    }

    @Override
    public void onRequestAddedFailure() {

    }

    @Override
    public void onRequestDeleteSuccess() {

    }

    @Override
    public void onRequestDeleteFailure() {

    }

    @Override
    public void onGetRequiredRequestsSuccess(ArrayList<Request> requests) {
        Log.v(TAG, "Request get successfully!!!!");
        for (Request r : requests) {
            Log.v(TAG, r.getDriverEmail() + " " + r.getRiderEmail() + " " + r.getEstimatedFare());
        }
    }

    @Override
    public void onGetRequiredRequestsFailure() {
        Log.v(TAG, "Requests get failure!!!!!!!");
    }
}
