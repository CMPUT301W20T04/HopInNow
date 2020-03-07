package com.example.hopinnow.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.google.gson.Gson;

public class RiderPaymentActivity extends AppCompatActivity {
    private Request curRequest;
    private Driver driver;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_payment);

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, null, car, null, null);
        //TODO set current Request
        mPrefs = getSharedPreferences("LocalRequest",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("CurrentRequest", "");
        curRequest = gson.fromJson(json, Request.class);

        //TODO create qr code


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
                Double prevRating = driver.getRating();
                int counts = driver.getRatingCounts();
                Double newRating = (prevRating+ratingBar.getRating())/(counts+1);
                driver.setRatingCounts(counts+1);
                driver.setRating(newRating);

                completeRequest(curRequest);

            }
        });

        //cancel rating and complete request
        Button cancelBtn= dialog.findViewById(R.id.dialog_rating_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                completeRequest(curRequest);

            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void completeRequest(Request req){
        //TODO req to trip list in firbase

        //TODO set curRequest as null in shared pref

        //TODO switch intent
    }

}
