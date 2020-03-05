package com.example.hopinnow.activities;

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


public class RiderDriverOfferFragment extends Fragment {
    Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_driver_offer, container);

        driver = null; //find driver

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if(view!=null)
        {
            //set driver name
            TextView driverName = view.findViewById(R.id.rider_driver_offer_name);
            driverName.setText(driver.getName());

            //set driver rating
            TextView driverRating = view.findViewById(R.id.rider_driver_offer_rating);
            driverRating.setText(driver.getRating());

            //set driver car
            TextView driverCar = view.findViewById(R.id.rider_driver_offer_car);
            String carInfo = driver.getCar().getColor()+" "+driver.getCar().getMake()+" "+driver.getCar().getMake();
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
                    // change intent
                }
            });

        }

        return view;
    }
}
