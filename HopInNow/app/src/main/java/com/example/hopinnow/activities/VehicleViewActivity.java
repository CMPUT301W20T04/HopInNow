package com.example.hopinnow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

import java.util.Locale;
import java.util.Objects;

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
        ViewGroup viewGroup = findViewById(R.id.vehicleInfo);
        progressbarDialog = new ProgressbarDialog(VehicleViewActivity.this, viewGroup);
        progressbarDialog.startProgressbarDialog();

        Intent intent = this.getIntent();
        this.currentDriver = (Driver) intent.getSerializableExtra("DriverObject");
        if (this.currentDriver == null) {
            this.driverDatabaseAccessor.getDriverProfile(this);
        } else {
            // set all text fields according to the retreived user object:
            this.vehicleMakeEditText.setText(currentDriver.getCar().getMake());
            this.vehicleModelEditText.setText(currentDriver.getCar().getModel());
            this.vehicleColorEditText.setText(currentDriver.getCar().getColor());
            this.vehiclePlateEditText.setText(currentDriver.getCar().getPlateNumber());
            this.progressbarDialog.dismissDialog();
        }
    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        // set all text fields according to the retreived user object:
        this.vehicleMakeEditText.setText(driver.getCar().getMake());
        this.vehicleModelEditText.setText(driver.getCar().getModel());
        this.vehicleColorEditText.setText(driver.getCar().getColor());
        this.vehiclePlateEditText.setText(driver.getCar().getPlateNumber());
        this.progressbarDialog.dismissDialog();
    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }
}
