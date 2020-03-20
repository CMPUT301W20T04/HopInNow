package com.example.hopinnow.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Driver;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;

public class DriverScanPaymentActivity extends AppCompatActivity {
    SurfaceView cameraView;
    Driver driver;
    BarcodeDetector qrDetector;
    CameraSource cameraSource;
    String encoded;
    RxPermissions rxPermissions;
    int permissionCount = 0;
    TextView permissionMsg;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_scanning);
        //TODO PASS DRIVER FROM DRIVER MAP ACTIVITY
        driver = (Driver) getIntent().getSerializableExtra("Driver");

        rxPermissions = new RxPermissions(DriverScanPaymentActivity.this);
        cameraView = findViewById(R.id.camera_scan_surfaceView);
        permissionMsg = findViewById(R.id.permission_camera_textView);

        qrDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this,qrDetector)
                .setRequestedPreviewSize(350,410)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // asking camera permission from driver
                if (ActivityCompat.checkSelfPermission(DriverScanPaymentActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    rxPermissions
                            .request(Manifest.permission.CAMERA)
                            .subscribe(granted -> {
                                if (granted) {
                                    cameraSource.start(cameraView.getHolder());
                                    permissionMsg.setVisibility(TextView.INVISIBLE);
                                } else {
                                    Toast.makeText(DriverScanPaymentActivity.this,
                                            "You must enable camera to receive your payment.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    permissionCount += 1;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                        permissionMsg.setVisibility(TextView.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });

        cameraView.setOnClickListener(v -> {
            // asking camera permission from driver
            if (ActivityCompat.checkSelfPermission(DriverScanPaymentActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {
                                cameraSource.start(cameraView.getHolder());
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



        });

        // detect qr code with camera
        qrDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCode = detections.getDetectedItems();
                if (qrCode.size()!=0){
                    encoded = qrCode.valueAt(0).displayValue;
                    String encodedPart[] = encoded.split(":");

                    // check if scanner is the corresponding driver
                    if (true){ //encodedPart[0].equals(driver.getEmail())
                        //double curDeposit = driver.getDeposit();
                        //driver.setDeposit(curDeposit + Double.parseDouble(encodedPart[1]));

                        //TODO SET FIREBASE REQUEST TO TRIP, TRIGGERS RIDER RATING

                        Toast.makeText(DriverScanPaymentActivity.this, "You have " +
                                "received " + encodedPart[1] + " QR Bucks!", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(DriverScanPaymentActivity.this,
                                DriverMapActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DriverScanPaymentActivity.this, "It seems like" +
                                "you have scanned the wrong QR code.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });
    }
}
