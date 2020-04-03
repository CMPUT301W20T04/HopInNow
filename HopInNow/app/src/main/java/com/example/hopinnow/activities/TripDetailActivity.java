package com.example.hopinnow.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.DriverObjectRetreieveListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderObjectRetrieveListener;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;
import com.google.type.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Author: Qianxi Li
 * Version: 1.0.0
 * Show the detail information of historical trip after clicking on one row in trip history
 */
public class TripDetailActivity extends AppCompatActivity implements DriverProfileStatusListener,
        UserProfileStatusListener, RiderProfileStatusListener, DriverObjectRetreieveListener, RiderObjectRetrieveListener {
    private TextView driverEmail;
    private TextView riderEmail;
    private TextView pickUpLocation;
    private TextView dropOffLocation;
    private TextView otherName;
    private TextView otherPhone;
    private RatingBar otherRating;
    private Driver driver;
    private Rider rider;
    private Driver otherDriver;
    private Rider otherRider;
    private Trip trip;
    private TextView rating;
    private TextView cost;
    private boolean type;
    private int search_key;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private RiderDatabaseAccessor riderDatabaseAccessor;
    private UserDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        driverEmail = findViewById(R.id.DriverEmail);
        riderEmail = findViewById(R.id.riderEmail);
        pickUpLocation = findViewById(R.id.pickUpLocation);
        dropOffLocation = findViewById(R.id.dropoffLocation);
        rating = findViewById(R.id.rating2);
        cost = findViewById(R.id.cost);
        otherName = findViewById(R.id.otherName);
        otherRating = findViewById(R.id.ratingBar2);
        otherRating.setVisibility(View.INVISIBLE);

        //get the key
        Intent intent = getIntent();
        search_key = intent.getIntExtra("pos_key",0);
        userDatabaseAccessor = new UserDatabaseAccessor();
        userDatabaseAccessor.getUserProfile(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.driver = driver;
        trip = driver.getDriverTripList().get(search_key);
        driverEmail.setText(" Driver Email: "+trip.getDriverEmail());
        riderEmail.setText(" Rider Email: "+trip.getRiderEmail());
        pickUpLocation.setText(String.format(" From: %s",trip.getPickUpLocName()));
        dropOffLocation.setText(String.format(" To: %s",trip.getDropOffLocName()));
        if (trip.getRating()==0){
            rating.setText(" Rating: not rated");
        } else {
            rating.setText(" Rating: "+trip.getRating().toString());
        }

        cost.setText(" Revenue: "+trip.getCost().toString());
        riderDatabaseAccessor.getRiderObject(trip.getRiderEmail(),this);

    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {

    }

    @Override
    public void onDriverProfileUpdateFailure() {

    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProfileRetrieveSuccess(User user) {

        this.type = user.isUserType();
        driverDatabaseAccessor = new DriverDatabaseAccessor();
        riderDatabaseAccessor = new RiderDatabaseAccessor();
        if(type){
            //driver
            driverDatabaseAccessor.getDriverProfile(this);
            ((TextView)findViewById(R.id.textView8)).setText("Rider Information");
        }
        else{
            otherRating.setVisibility(View.INVISIBLE);
            riderDatabaseAccessor.getRiderProfile(this);
        }



    }

    /**
     * Shows driver information and contact means on a dialog - Added By Viola
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    public void showInfo(Driver d,Rider r){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_driver_info);

        if (d==null && r!=null){
            //set rider name
            TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
            driverName.setText(r.getName());

            //set driver specific information gone
            dialog.findViewById(R.id.dialog_driver_plate_label).setVisibility(View.GONE);
            dialog.findViewById(R.id.dialog_driver_car_label).setVisibility(View.GONE);
            TextView ra = dialog.findViewById(R.id.dialog_driver_rating);
            ra.setCompoundDrawables(null,null,null,null);
            ra.setText("Contact Rider By: ");
            ra.setTextSize(18);
            dialog.findViewById(R.id.dialog_driver_car).setVisibility(View.GONE);
            dialog.findViewById(R.id.dialog_driver_plate).setVisibility(View.GONE);

            //call rider
            Button callBtn= dialog.findViewById(R.id.dialog_call_button);
            callBtn.setOnClickListener(v -> callNumber(r.getPhoneNumber()));

            //email rider
            Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
            emailBtn.setOnClickListener(v -> emailDriver(r.getEmail()));

        } else if (d!=null && r==null){
            //set driver name
            TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
            driverName.setText(d.getName());

            //set driver rating
            TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
            String rating;
            if (d.getRating()==0){
                rating = "not yet rated";
            } else {
                rating = Double.toString(d.getRating());
            }
            driverRating.setText(rating);

            //set driver car
            TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
            String carInfo = d.getCar().getColor() + " " + d.getCar().getMake() + " " + d.getCar().getModel();
            driverCar.setText(carInfo);

            //set driver license
            TextView driverLicense = dialog.findViewById(R.id.dialog_driver_plate);
            driverLicense.setText(d.getCar().getPlateNumber());

            //call driver
            Button callBtn= dialog.findViewById(R.id.dialog_call_button);
            callBtn.setOnClickListener(v -> callNumber(d.getPhoneNumber()));

            //email driver
            Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
            emailBtn.setOnClickListener(v -> emailDriver(d.getEmail()));
        }


        dialog.show();

    }

    /**
     * Starts phone calling. - Added By Viola
     * @param phoneNumber
     *      the phone number to be called
     */
    @SuppressLint("CheckResult")
    public void callNumber(String phoneNumber){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(TripDetailActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.CALL_PHONE)
                    .subscribe(granted -> {
                        if (granted) {
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(this,"User's Phone Number: " + phoneNumber,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            startActivity(callIntent);
        }
    }


    /*
    Stackoverflow post by Dira
    https://stackoverflow.com/questions/8701634/send-email-intent
    Answer by Dira (code from the question itself)
     */
    /**
     * Prompts email app selection and directs to email drafting page with auto0filled email address
     * of the driver. - Added By Viola
     * @param email
     *      the driver's email address
     */
    public void emailDriver(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        startActivity(Intent.createChooser(intent, "Send Email"));
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onRiderProfileRetrieveSuccess(Rider rider) {
        this.rider = rider;
        trip = this.rider.getRiderTripList().get(search_key);
        driverEmail.setText(" Driver Email:  "+trip.getDriverEmail());
        riderEmail.setText(" Rider Email:  "+trip.getRiderEmail());
        pickUpLocation.setText(String.format(" From: %s",trip.getPickUpLocName()));
        dropOffLocation.setText(String.format(" To: %s",trip.getDropOffLocName()));
        if (trip.getRating()==0){
            rating.setText(" Rating: not rated");
        } else {
            rating.setText(" Rating: "+trip.getRating().toString());
        }
        cost.setText(" Cost:  "+trip.getCost().toString());
        driverDatabaseAccessor.getDriverObject(trip.getDriverEmail(),this);

    }

    @Override
    public void onRiderProfileRetrieveFailure() {

    }

    @Override
    public void onRiderProfileUpdateSuccess(Rider rider) {

    }

    @Override
    public void onRiderProfileUpdateFailure() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDriverObjRetrieveSuccess(Driver driver) {
        this.otherDriver = driver;
        if (this.otherDriver.getRating() == null){
            this.otherDriver.setRating(0.0);
        }
        otherName.setText(" Driver Name:  "+this.otherDriver.getName());
        otherName.setOnClickListener(v -> showInfo(this.otherDriver,null));
        otherRating.setNumStars(5);
        float rating3 = (float)this.otherDriver.getRating().doubleValue();
        otherRating.setRating(rating3);
        otherRating.setVisibility(View.VISIBLE);
        otherRating.setIsIndicator(true);
    }

    @Override
    public void onDriverObjRetrieveFailure() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRiderObjRetrieveSuccess(Rider rider) {
        this.otherRider = rider;
        otherName.setText(" Rider Name:  "+this.otherRider.getName());
        otherName.setOnClickListener(v -> showInfo(null,this.otherRider));
    }

    @Override
    public void onRiderObjRetrieveFailure() {

    }
}
