package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hopinnow.R;
import com.example.hopinnow.Database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.LoginStatusListener;
import com.example.hopinnow.statuslisteners.RegisterStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

public class RegisterVehicleInfoActivity extends AppCompatActivity implements LoginStatusListener,
        RegisterStatusListener, UserProfileStatusListener {
    private UserDatabaseAccessor userDatabaseAccessor;
    // the user object past:
    private Driver driver;
    // the four part information about the vehicle:
    private EditText make;
    private EditText model;
    private EditText color;
    private EditText plateNumber;
    // finish registration button:
    private Button finishBtn;
    // alert progress dialog:
    private ProgressbarDialog progressbarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);
        // init user database accessor:
        this.userDatabaseAccessor = new UserDatabaseAccessor();
        // get the information already filled:
        this.driver = (Driver)getIntent().getSerializableExtra("DriverObject");
        // connect UI components:
        this.make = findViewById(R.id.vehMakeEt);
        this.model = findViewById(R.id.vehModelEt);
        this.color = findViewById(R.id.vehColorEt);
        this.plateNumber = findViewById(R.id.vehPlateEt);
        this.finishBtn = findViewById(R.id.vehicleToFinishBtn);
        // init progress bar:
        ViewGroup viewGroup = findViewById(R.id.vehicleInfo);
        this.progressbarDialog = new ProgressbarDialog(this, viewGroup);
    }
    @Override
    protected void onStart() {
        super.onStart();
        this.finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbarDialog.startProgressbarDialog();
                String makeData = make.getText().toString();
                String modelData = model.getText().toString();
                String colorData = color.getText().toString();
                String plateNumberData = plateNumber.getText().toString();
                Car car = new Car(makeData, modelData, colorData, plateNumberData);
                driver.setCar(car);
                userDatabaseAccessor.registerUser(driver, RegisterVehicleInfoActivity.this);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        // first dismiss the progress bar:
        this.progressbarDialog.dismissDialog();
        // initialize intent to go to the ProfileActivity:
        Intent intent = new Intent(getApplicationContext(), DriverMapActivity.class);
        Bundle bundle = new Bundle();
        // put the driver object into the bundle, Profile activity can access directly:
        bundle.putSerializable("UserObject", this.driver);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        // show success message here:
        Toast.makeText(getApplicationContext(),
                "Driver logged in successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure() {
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Driver login failed, try again later!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getApplicationContext(),
                "Driver registered successfully!", Toast.LENGTH_SHORT).show();
        this.userDatabaseAccessor.createUserProfile(this.driver, this);
    }

    @Override
    public void onRegisterFailure() {
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Register failed, try again later!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileStoreSuccess() {
        // after the profile is stored should the driver be logged in:
        this.userDatabaseAccessor.loginUser(this.driver, this);
    }

    @Override
    public void onProfileStoreFailure() {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileRetrieveFailure() {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileUpdateSuccess(User user) {
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onProfileUpdateFailure() {
        this.progressbarDialog.dismissDialog();
    }
}
