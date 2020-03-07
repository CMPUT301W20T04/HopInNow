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

public class RiderWaitingPickupFragment extends Fragment {
    Request curRequest;
    Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_waiting_pickup, container, false);

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, null, car, null, null);
        //TODO set current Request
        curRequest = ((RiderMapActivity) getActivity()).retrieveCurrentRequest();

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if (view != null) {
            //set driver name
            TextView driverName = view.findViewById(R.id.rider_waiting_driver_name);
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
                    ((RiderMapActivity) getActivity()).callNumber(driver.getPhoneNumber());
                }
            });

            // Click this button to email driver
            Button emailBtn = view.findViewById(R.id.rider_waiting_email_button);
            emailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity) getActivity()).emailDriver(driver.getEmail());
                }
            });

            // Click this button to cancel request
            // Change fragment
            Button cancelBtn = view.findViewById(R.id.rider_waiting_cancel_button);
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //change fragment
                    ((RiderMapActivity) getActivity()).cancelRequest();
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
        if (driver.getRatingCounts()==0){
            rating = "not yet rated";
        } else {
            rating = Double.toString(driver.getRating());
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
                ((RiderMapActivity) getActivity()).callNumber(driver.getPhoneNumber());
            }
        });

        //driver name
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiderMapActivity) getActivity()).emailDriver(driver.getEmail());
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }



}





