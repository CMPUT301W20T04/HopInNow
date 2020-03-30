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
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

import java.util.Locale;
import java.util.Objects;

/**
 * Author: Peter Zhou
 * Editor: Shway Wang
 * Version: 1.0.2
 * This class is to show the vehicle info and user can update it
 */
public class VehicleViewActivity extends AppCompatActivity implements DriverProfileStatusListener {

    private Button updateBtn;
    private EditText vehicleMakeEditText;
    private EditText vehicleModelEditText;
    private EditText vehicleColorEditText;
    private EditText vehiclePlateEditText;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private ProgressbarDialog progressbarDialog;
    private Driver currentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_view);

        this.driverDatabaseAccessor = new DriverDatabaseAccessor();
        this.vehicleMakeEditText = findViewById(R.id.vehicleMakeEditText);
        this.vehicleModelEditText = findViewById(R.id.vehicleModelEditText);
        this.vehicleColorEditText = findViewById(R.id.vehicleColorEditText);
        this.vehiclePlateEditText = findViewById(R.id.vehiclePlateEditText);
        progressbarDialog = new ProgressbarDialog(VehicleViewActivity.this);
        progressbarDialog.startProgressbarDialog();

        Intent intent = this.getIntent();
        this.currentDriver = (Driver) intent.getSerializableExtra("DriverObject");
        if (this.currentDriver == null) {
            this.driverDatabaseAccessor.getDriverProfile(this);
        } else {
            if (this.currentDriver.getCar() == null) {
                // set all text fields according to the retrieved user object:
                this.vehicleMakeEditText.setText("");
                this.vehicleModelEditText.setText("");
                this.vehicleColorEditText.setText("");
                this.vehiclePlateEditText.setText("");
                this.progressbarDialog.dismissDialog();
            } else {
                // set all text fields according to the retrieved user object:
                this.vehicleMakeEditText.setText(currentDriver.getCar().getMake());
                this.vehicleModelEditText.setText(currentDriver.getCar().getModel());
                this.vehicleColorEditText.setText(currentDriver.getCar().getColor());
                this.vehiclePlateEditText.setText(currentDriver.getCar().getPlateNumber());
                this.progressbarDialog.dismissDialog();
            }
        }

        this.updateBtn = findViewById(R.id.vehicleUpdateBtn);
        this.updateBtn.setOnClickListener(v -> {
            Car car = new Car();
            car.setMake(vehicleMakeEditText.getText().toString());
            car.setModel(vehicleModelEditText.getText().toString());
            car.setColor(vehicleColorEditText.getText().toString());
            car.setPlateNumber(vehiclePlateEditText.getText().toString());
            this.currentDriver.setCar(car);
            // access database:
            driverDatabaseAccessor.updateDriverProfile(this.currentDriver,
                    VehicleViewActivity.this);
        });
    }
    // wrapper function to set text for car information:
    private void setCarInfo(Car car) {
        if (car == null) {
            // set all text fields according to the retrieved user object:
            this.vehicleMakeEditText.setText("");
            this.vehicleModelEditText.setText("");
            this.vehicleColorEditText.setText("");
            this.vehiclePlateEditText.setText("");
        } else {
            // set all text fields according to the retreived user object:
            this.vehicleMakeEditText.setText(car.getMake());
            this.vehicleModelEditText.setText(car.getModel());
            this.vehicleColorEditText.setText(car.getColor());
            this.vehiclePlateEditText.setText(car.getPlateNumber());
        }
    }
    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.setCarInfo(driver.getCar());
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onDriverProfileRetrieveFailure() {
        if (this.currentDriver.getCar() == null) {
            this.setCarInfo(null);
            this.progressbarDialog.dismissDialog();
        } else {
            this.setCarInfo(this.currentDriver.getCar());
            this.progressbarDialog.dismissDialog();
        }
    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {
        this.currentDriver = driver;
        this.setCarInfo(this.currentDriver.getCar());
        this.progressbarDialog.dismissDialog();
        Toast.makeText(getApplicationContext(),
                "Your info is updated!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDriverProfileUpdateFailure() {
        Toast.makeText(getApplicationContext(),
                "Update failed, please try again!", Toast.LENGTH_LONG).show();
    }
}
