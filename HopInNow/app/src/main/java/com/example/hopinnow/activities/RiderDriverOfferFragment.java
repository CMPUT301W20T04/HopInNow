package com.example.hopinnow.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;


public class RiderDriverOfferFragment extends Fragment {
    Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rider_driver_offer, container,false);

        //TODO assign driver
        Car car = new Car("Hudson","Speedster","Cream","111111");
        driver = new Driver("12345678", "12345678", "Lupin the Third",
                "12345678", true, null, car, null, null);

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if(view!=null)
        {
            //set driver name
            TextView driverName = view.findViewById(R.id.rider_driver_offer_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shows driver information
                    showDriverInfo();
                }
            });

            //set driver rating
            TextView driverRating = view.findViewById(R.id.rider_driver_offer_rating);
            String rating;
            if (driver.getRating()==-1){
                rating = "not yet rated";
            } else {
                rating = Integer.toString(driver.getRating());
            }
            driverRating.setText(rating);

            //set driver car
            TextView driverCar = view.findViewById(R.id.rider_driver_offer_car);
            String carInfo = driver.getCar().getColor()+" "+driver.getCar().getMake()+" "+driver.getCar().getModel();
            driverCar.setText(carInfo);

            //set driver license
            TextView driverLicense = view.findViewById(R.id.rider_driver_offer_plate);
            driverLicense.setText(driver.getCar().getPlateNumber());

            // Click this button to accept request
            // Change fragment
            Button acceptBtn = view.findViewById(R.id.rider_driver_offer_accept_button);
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //change fragment
                    ((RiderMapActivity)getActivity()).switchFragment(R.layout.fragment_rider_waiting_pickup);
                }
            });

            // Click this button to accept request
            // Change fragment
            Button declineBtn = view.findViewById(R.id.rider_driver_offer_decline_button);
            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO intent
                }
            });

        }

        return view;
    }

    /**
     * Shows driver information and contact means on a dialog
     */
    public void showDriverInfo(){
        //change fragment
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_driver_info);

        //driver name
        TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
        driverName.setText(driver.getName());

        //set driver rating
        TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
        String rating;
        if (driver.getRating()==-1){
            rating = "not yet rated";
        } else {
            rating = Integer.toString(driver.getRating());
        }
        driverRating.setText(rating);

        //set driver car
        TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
        String carInfo = driver.getCar().getColor()+" "+driver.getCar().getMake()+" "+driver.getCar().getModel();
        driverCar.setText(carInfo);

        //set driver license
        TextView driverLicense = dialog.findViewById(R.id.dialog_driver_plate);
        driverLicense.setText(driver.getCar().getPlateNumber());

        //driver name
        Button callBtn= dialog.findViewById(R.id.dialog_call_button);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDriver();
            }
        });

        //driver name
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDriver();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void callDriver(){
        //TODO
    }

    public void emailDriver(){
        //TODO
    }
}
