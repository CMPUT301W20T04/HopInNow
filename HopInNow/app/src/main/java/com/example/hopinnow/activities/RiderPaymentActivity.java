package com.example.hopinnow.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.helperclasses.QRCodeHelper;
import com.google.gson.Gson;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class RiderPaymentActivity extends AppCompatActivity {
    private Request curRequest;
    private Driver driver;
    private Rider rider;
    private SharedPreferences mPrefs;
    private Double totalPayment;
    private Double baseFare = curRequest.getEstimatedFare();
    private ImageView qrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_payment);

        qrImage = findViewById(R.id.rider_payment_qr);

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, null, car, null, null);
        rider = new Rider("1@gmail.com","111111","Me","7654321",false,curRequest,null);
        //TODO set current Request
        mPrefs = getSharedPreferences("LocalRequest",MODE_PRIVATE);
        Gson gsonRequest = new Gson();
        String json = mPrefs.getString("CurrentRequest", "");
        curRequest = gsonRequest.fromJson(json, Request.class);


        //TODO create qr code
        //Double availableRiderDeposit = rider.getDeposit();
        final Button confirmPaymentBtn = findViewById(R.id.rider_payment_submit_tips);
        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CHANGE INTENT TO PAYMENT
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

                newDriverRating(ratingBar);
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

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void newDriverRating(RatingBar rb){
        Double prevRating = driver.getRating();
        int counts = driver.getRatingCounts();
        Double newRating = (prevRating + rb.getRating())/(counts+1);
        driver.setRatingCounts(counts+1);
        driver.setRating(newRating);
    }

    private void completeRequest(){
        //TODO req to trip list in firbase

        //TODO set curRequest as null in shared pref
        //curRequest = null;
        /**mPrefs = getSharedPreferences("LocalRequest", MODE_PRIVATE);
         SharedPreferences.Editor prefsEditor = mPrefs.edit();
         Gson gson = new Gson();
         String json = gson.toJson(curRequest); // myObject - instance of MyObject
         prefsEditor.putString("CurrentRequest", json);
         prefsEditor.apply();*/

        //TODO switch intent
        Intent intent = new Intent(RiderPaymentActivity.this,RiderPaymentActivity.class);
        startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rider_payment_ten:
                if (checked)
                    totalPayment = baseFare*1.1;
                break;
            case R.id.rider_payment_fifteen:
                if (checked)
                    totalPayment = baseFare*1.15;
                break;
            case R.id.rider_payment_twenty:
                if (checked)
                    totalPayment = baseFare*1.2;
                break;
            case R.id.rider_payment_else:
                if (checked)
                    totalPayment = baseFare* (1+ (getOtherTip()));
                break;
        }
    }

    /**
     * Returns rider input tip amount
     */
    private int getOtherTip(){
        EditText otherTip = findViewById(R.id.rider_payment_other_editText);
        int tipPercentage = Integer.parseInt(otherTip.getText().toString())/100;
        return tipPercentage;
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

}