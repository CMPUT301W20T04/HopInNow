package com.example.hopinnow.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.DriverRequestDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.google.zxing.Result;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Author: Tianyu Bai
 * This class deals with the QR code and the payment process:
 */
public class DriverScanPaymentActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler, DriverRequestListener,
        DriverProfileStatusListener, AvailRequestListListener {
    private ZXingScannerView cameraView;
    private Driver driver;
    private Request curRequest;
    private String encoded;
    private RxPermissions rxPermissions;
    private int permissionCount = 0;
    private TextView permissionMsg;
    private DriverRequestDatabaseAccessor driverRequestDatabaseAccessor = new DriverRequestDatabaseAccessor();
    private DriverDatabaseAccessor driverDatabaseAccessor = new DriverDatabaseAccessor();


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_scanning);
        driver = (Driver) getIntent().getSerializableExtra("Driver");
        //curRequest
        curRequest = driver.getCurRequest();

        rxPermissions = new RxPermissions(DriverScanPaymentActivity.this);
        cameraView = findViewById(R.id.camera_scan_view);
        permissionMsg = findViewById(R.id.permission_camera_textView);

        if (ActivityCompat.checkSelfPermission(DriverScanPaymentActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            permissionMsg.setVisibility(TextView.INVISIBLE);
            cameraView.setResultHandler(this);
            cameraView.startCamera();
        } else {
            cameraPermission();
        }

        cameraView.setOnClickListener(v -> {
            cameraPermission();
        });

    }


    @Override
    public void handleResult(Result rawResult){
        encoded = rawResult.getText();
        String result[] = encoded.split(":");
        if (driver.getEmail().equals(result[0])){ //

            //todo trigger rider rating by removing request from firebase
            //double prevDeposit = driver.getDeposit();
            //driver.setDeposit(prevDeposit + Double.valueOf(result[1]));
            Toast.makeText(this, "You have successfully received " + result[1] +
                    " QR bucks for you completed ride!", Toast.LENGTH_SHORT).show();

            //driver complete the request and trigger the rider to rate.
            driverRequestDatabaseAccessor.driverCompleteRequest(curRequest,this);

            Intent intent = new Intent(DriverScanPaymentActivity.this, DriverMapActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "This QR code does not belong to the trip " +
                    "that you have completed.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CheckResult")
    private void cameraPermission(){
        if (ActivityCompat.checkSelfPermission(DriverScanPaymentActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            cameraView.setResultHandler(this);
                            cameraView.startCamera();
                            permissionMsg.setVisibility(TextView.INVISIBLE);
                        } else {
                            Toast.makeText(DriverScanPaymentActivity.this,
                                    "You must enable camera to receive your payment.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            permissionCount += 1;

            // if driver has denied camera permission three times
            if ((ActivityCompat.checkSelfPermission(DriverScanPaymentActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &&
                    (permissionCount > 3)) {
                Toast.makeText(DriverScanPaymentActivity.this, "Guess you " +
                                "decided to donate your earning to the HopInNow Team, thanks! :D"
                        , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DriverScanPaymentActivity.this,
                        DriverMapActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDriverRequestAccept() {

    }

    @Override
    public void onDriverRequestTimeoutOrFail() {

    }

    @Override
    public void onRequestAlreadyTaken() {

    }

    @Override
    public void onRequestInfoChange(Request request) {

    }

    @Override
    public void onRequestAcceptedByRider(Request request) {

    }

    @Override
    public void onRequestDeclinedByRider() {

    }

    @Override
    public void onDriverPickupSuccess() {

    }

    @Override
    public void onDriverPickupFail() {

    }

    @Override
    public void onDriverRequestCompleteSuccess() {
        driverRequestDatabaseAccessor.driverWaitOnRating(curRequest,this);
    }

    @Override
    public void onDriverRequestCompleteFailure() {

    }

    @Override
    public void onWaitOnRatingSuccess() {
        //means the rider update the rating successfully.
        driverDatabaseAccessor.getDriverProfile(this);


    }

    @Override
    public void onWaitOnRatingError() {

    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.driver = driver;
        Date current_time = new Date();


        ArrayList<Trip> driverTripList = this.driver.getDriverTripList();
        if(driverTripList == null){
            driverTripList = new ArrayList<>();

        }
        driverTripList.add(new Trip(curRequest.getDriverEmail(),curRequest.getRiderEmail(),
                curRequest.getPickUpLoc(),curRequest.getDropOffLoc(),
                curRequest.getPickUpLocName(),curRequest.getDropOffLocName(),
                (Date)curRequest.getPickUpDateTime(),  (Date)current_time,
                (int)Math.abs(current_time.getTime() -
                        curRequest.getPickUpDateTime().getTime()),
                curRequest.getCar(),curRequest.getEstimatedFare(),curRequest.getRating()));
        this.driver.setDriverTripList(driverTripList);
        driverDatabaseAccessor.updateDriverProfile(this.driver,this);
    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {
        driverRequestDatabaseAccessor.deleteRequest(this);
    }

    @Override
    public void onDriverProfileUpdateFailure() {

    }

    @Override
    public void onRequestAddedSuccess() {

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

    }

    @Override
    public void onGetRequiredRequestsFailure() {

    }

    @Override
    public void onAllRequestsUpdateSuccess(ArrayList<Request> requests) {

    }

    @Override
    public void onAllRequestsUpdateError() {

    }
}