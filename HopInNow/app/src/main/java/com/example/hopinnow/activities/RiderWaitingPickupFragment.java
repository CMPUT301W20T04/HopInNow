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
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;

public class RiderWaitingPickupFragment extends Fragment {
    Request curRequest;
    Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_waiting_pickup, container);

        driver = null; //TODO set driver
        curRequest = null; //TODO set curRequest

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if(view!=null)
        {
            //set driver name
            TextView driverName = view.findViewById(R.id.rider_waiting_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shows driver information
                    showDriverInfo();
                }
            });

            //set pick up location
            TextView pickUpLoc = view.findViewById(R.id.rider_waiting_pickUp);
            pickUpLoc.setText(curRequest.getPickUpLocName());

            //set drop off location
            TextView dropOffLoc = view.findViewById(R.id.rider_waiting_dropOff);
            dropOffLoc.setText(curRequest.getDropOffLocName());

            //set estimated fare
            TextView estimatedFare = view.findViewById(R.id.rider_waiting_fare);
            estimatedFare.setText(Double.toString(curRequest.getEstimatedFare()));

            // Click this button to call driver
            Button callBtn = view.findViewById(R.id.rider_waiting_call_button);
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDriver();
                }
            });

            // Click this button to email driver
            Button emailBtn = view.findViewById(R.id.rider_waiting_email_button);
            emailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailDriver();
                }
            });

            // Click this button to cancel request
            // Change fragment
            Button cancelBtn = view.findViewById(R.id.rider_driver_offer_accept_button);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //change fragment
                    ((RiderMapActivity)getActivity()).cancelRequest(R.layout.fragment_rider_waiting_pickup);
                }
            });

        }

        return view;
    }



    /**
     * Shows driver information and contact means on a dialog
     */
    private void showDriverInfo(){
        //change fragment
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_driver_info);
        dialog.setTitle("Driver Information");

        //driver name
        TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
        driverName.setText(driver.getName());

        //set driver rating
        TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
        driverRating.setText(driver.getRating());

        //set driver car
        TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
        String carInfo = driver.getCar().getColor()+" "+driver.getCar().getMake()+" "+driver.getCar().getMake();
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

