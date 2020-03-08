package com.example.hopinnow.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.helperclasses.QRCodeHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class RiderPaymentActivity extends AppCompatActivity {
    private Request curRequest;
    private Driver driver;
    private Rider rider;
    private SharedPreferences mPrefs;
    private Double totalPayment = 0.00;
    private Double myTip;
    private Double baseFare;
    private ImageView qrImage;
    private Boolean other = false;
    private TextView totalPaymentTextview;
    private Date dropOffDateTime;
    private Double myRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_payment);

        qrImage = findViewById(R.id.rider_payment_qr);
        dropOffDateTime = Calendar.getInstance().getTime();

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, 10.0, null, car, null, null);
        rider = new Rider("1@gmail.com","111111","Me","7654321",false,10.0, curRequest,null);
        //TODO set current Request
        mPrefs = getSharedPreferences("LocalRequest",MODE_PRIVATE);
        Gson gsonRequest = new Gson();
        String json = mPrefs.getString("CurrentRequest", "");
        curRequest = gsonRequest.fromJson(json, Request.class);

        baseFare = curRequest.getEstimatedFare();
        totalPaymentTextview = findViewById(R.id.rider_payment_total);
        totalPaymentTextview.setText("$ "+Double.toString(baseFare));


        //show total payment calculation by baes fare * my tips
        Button showTotalBtn = findViewById(R.id.rider_payment_calculate);
        showTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMyTip();
                totalPayment = formatTotalPayment();
                totalPaymentTextview.setText("$ "+Double.toString(totalPayment));
            }
        });



        // creates QR code on button confirm
        final Button confirmPaymentBtn = findViewById(R.id.rider_payment_submit_tips);
        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMyTip();
                totalPayment = formatTotalPayment();
                totalPayment = Double.parseDouble(Double.toString(totalPayment));

                if (totalPayment>rider.getDeposit()){
                    Toast.makeText(RiderPaymentActivity.this, "There is insufficient deposit in your account!", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gsonPay = new Gson();
                    String serializePay = gsonPay.toJson(totalPayment);

                    Bitmap bitmap = QRCodeHelper
                            .newInstance(RiderPaymentActivity.this)
                            .setContent(serializePay)
                            .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                            .setMargin(2)
                            .getQRCOde();
                    qrImage.setImageBitmap(bitmap);
                    confirmPaymentBtn.setVisibility(View.GONE);

                    onScanningCompleted();
                }
                }


        });




    }

    /**
     * Shows driver information and contact means on a dialog
     */
    public void showRatingDialog(){
        //change fragment
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_rider_rating);

        //driver name
        TextView driverName= dialog.findViewById(R.id.dialog_rider_rating_driver);
        driverName.setText(driver.getName());

        //assign rating bar
        final RatingBar ratingBar = dialog.findViewById(R.id.dialog_rating_bar);


        //submit rating and complete request
        Button submitBtn= dialog.findViewById(R.id.dialog_rating_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNewDriverRating(ratingBar);
                completeRequest();

            }
        });

        //cancel rating and complete request
        Button cancelBtn= dialog.findViewById(R.id.dialog_rating_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRequest();
            }
        });

        //dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void setNewDriverRating(RatingBar rb){
        Double prevRating = driver.getRating();
        int counts = driver.getRatingCounts();
        myRating = (double) rb.getRating();
        Double newRating = (prevRating + rb.getRating())/(counts+1);
        driver.setRatingCounts(counts+1);
        driver.setRating(newRating);
    }

    private void completeRequest(){
        //TODO req to trip list in rider's trip list in firbase
        Trip trip = toTrip();

        Intent intent = new Intent(RiderPaymentActivity.this,RiderMapActivity.class);
        intent.putExtra("Current_Request_To_Null", true);
        startActivity(intent);

        Toast.makeText(RiderPaymentActivity.this, "Your trip is completed!", Toast.LENGTH_SHORT).show();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rider_payment_ten:
                if (checked)
                    myTip = 0.1;
                    other = false;
                break;
            case R.id.rider_payment_fifteen:
                if (checked)
                    myTip = 0.15;
                    other = false;
                break;
            case R.id.rider_payment_twenty:
                if (checked)
                    myTip = 0.2;
                    other = false;
                break;
            case R.id.rider_payment_else:
                if (checked)
                    other = true;
                    myTip = 0.0;
                break;
        }

    }

    /**
     * Returns rider input tip amount
     */
    private void getOtherTip(){
        EditText otherTip = findViewById(R.id.rider_payment_other_editText);
        if (!otherTip.getText().toString().isEmpty()) {
            myTip = (Double.parseDouble(otherTip.getText().toString())) / 100;
        }

    }


    /**
     * Complete Transaction
     * Pops up rating dialog after driver finishes scanning QR from rider
     */
    public void onScanningCompleted(){
        Double newDepositAmount = rider.getDeposit()-totalPayment;
        rider.setDeposit(newDepositAmount);
        showRatingDialog();
    }

    /**
     * set my tip if customized amount is entered
     */
    private void setMyTip(){
        if (other){
            getOtherTip();
        }
    }

    /**
     * format total payment to double with two digits
     */
    private Double formatTotalPayment(){
        return Double.parseDouble(new DecimalFormat("##.##").format((1 + myTip)*baseFare));
    }

    /**
     * transform request to trip
     */
    private Trip toTrip(){
        int duration = (int) (curRequest.getPickUpDateTime().getTime() - dropOffDateTime.getTime());
        LatLng pickUpLoc = curRequest.getPickUpLoc();
        LatLng dropOffLoc = curRequest.getPickUpLoc();
        String dropOffName = curRequest.getDropOffLocName();
        String pickUpName = curRequest.getPickUpLocName();
        Date pickUpTime = curRequest.getPickUpDateTime();
        Car car = driver.getCar();
        Trip trip = new Trip(driver,rider,pickUpLoc,dropOffLoc,pickUpName,dropOffName,pickUpTime,
                dropOffDateTime, duration, car,totalPayment,myRating);
        return trip;
    }



}