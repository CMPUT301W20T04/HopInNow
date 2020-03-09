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
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;

import java.util.Objects;

/**
 * Authoer: Tianyu Bai
 * This class defines the fargment while rider is waiting for driver pickup.
 * This class is triggered by rider accepting driver's offer.
 */
public class RiderWaitingPickupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_waiting_pickup, container,
                false);

        // set local variables
        Request curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity())).retrieveCurrentRequest();
        Driver driver = curRequest.getDriver();

        if (view != null) {

            //set driver name
            TextView driverName = view.findViewById(R.id.rider_waiting_driver_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).showDriverInfo();
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


            //TODO TEMPORARY
            Button nextBtn = view.findViewById(R.id.rider_waiting_next_button);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity)getActivity()).switchFragment(R.layout.fragment_rider_pickedup);
                }
            });

        }

        return view;
    }



}





