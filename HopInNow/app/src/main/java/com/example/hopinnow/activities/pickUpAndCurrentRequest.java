package com.example.hopinnow.activities;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.RequestDatabaseAccessor;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

import java.util.ArrayList;

public class pickUpAndCurrentRequest extends Fragment implements DriverProfileStatusListener {
    private Driver driver;
    private Request request;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DriverDatabaseAccessor driverDatabaseAccessor = new DriverDatabaseAccessor();
        //UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
        int display_mode;
        //here get the driver from database
        //userDatabaseAccessor.getUserProfile(this);
        //driverDatabaseAccessor.driverAcceptRequest();
        driverDatabaseAccessor.getDriverProfile(this);


        View view = inflater.inflate(R.layout.fragment_driver_pick_rider_up, container,false);

        if(view!=null)
        {
            //Driver driver, Rider rider, LatLng pickUpLoc, LatLng dropOffLoc, String pickUpLocName,
            // String dropOffLocName,  Date pickUpDateTime,
            //                    Car car, Double estimatedFare
            TextView requestTitleText = view.findViewById(R.id.RequestInfoText);
            TextView requestFromText = view.findViewById(R.id.requestFromText);
            TextView requestToText = view.findViewById(R.id.requestToText);
            TextView requestTimeText = view.findViewById(R.id.requestTimeText);
            TextView requestCostText = view.findViewById(R.id.requestCostText);
            Button pickUpButton = view.findViewById(R.id.PickUpRiderButton);
            Button dropOffButton = view.findViewById(R.id.dropOffRiderButton);
            Button emergencyCallButton = view.findViewById(R.id.EmergencyCall);
            display_mode = ((DriverMapActivity)getActivity()).getCurrentRequestPageCounter();
            /*
            requestFromText.setText("123");
            requestToText.setText("345");
            requestTimeText.setText("12:28");
            requestCostText.setText("12");*/
            if(display_mode == 0){
                pickUpButton.setVisibility(View.VISIBLE);
                dropOffButton.setVisibility(View.INVISIBLE);
                emergencyCallButton.setVisibility(View.INVISIBLE);
                ((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(1);
            }else{
                pickUpButton.setVisibility(View.INVISIBLE);
                dropOffButton.setVisibility(View.VISIBLE);
                emergencyCallButton.setVisibility(View.VISIBLE);
                ((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(0);
            }

            /*
            requestFromText.setText("From: "+);
            requestToText.setText("To: "+);
            requestTimeText.setText("Time: "+);
            requestCostText.setText("Estimate Fare: "+);*/
            if(display_mode == 0){
                pickUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_pick_rider_up); }
                });}
            else{
                dropOffButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_requests);
                    }
                });
                emergencyCallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DriverMapActivity)getActivity()).callNumber("7806041057");//shway number
                    }
                });}

        }
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {

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


    /*
    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.driver = (Driver)user;

    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }*/
}
