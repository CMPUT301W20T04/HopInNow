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
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class pickUpAndCurrentRequest extends Fragment implements DriverProfileStatusListener {
    private Driver driver;
    private Request request;
    TextView requestTitleText;
    TextView requestFromText;
    TextView requestToText;
    TextView requestTimeText;
    TextView requestCostText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DriverDatabaseAccessor driverDatabaseAccessor = new DriverDatabaseAccessor();
        int display_mode;
        //here get the driver from database
        driverDatabaseAccessor.getDriverProfile(this);
        View view = inflater.inflate(R.layout.fragment_driver_pick_rider_up, container,false);
        if(view!=null)
        {
            requestTitleText = view.findViewById(R.id.RequestInfoText);
            requestFromText = view.findViewById(R.id.requestFromText);
            requestToText = view.findViewById(R.id.requestToText);
            requestTimeText = view.findViewById(R.id.requestTimeText);
            requestCostText = view.findViewById(R.id.requestCostText);
            Button pickUpButton = view.findViewById(R.id.PickUpRiderButton);
            Button dropOffButton = view.findViewById(R.id.dropOffRiderButton);
            Button emergencyCallButton = view.findViewById(R.id.EmergencyCall);
            display_mode = ((DriverMapActivity)getActivity()).getCurrentRequestPageCounter();
            if(display_mode == 0){
                // the fragment that display the pickup button and request information
                pickUpButton.setVisibility(View.VISIBLE);
                dropOffButton.setVisibility(View.INVISIBLE);
                emergencyCallButton.setVisibility(View.INVISIBLE);
                ((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(1);
            }else{
                // the fragment that display the drop off button and request information
                pickUpButton.setVisibility(View.INVISIBLE);
                dropOffButton.setVisibility(View.VISIBLE);
                emergencyCallButton.setVisibility(View.VISIBLE);
                ((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(0);
            }
            if(display_mode == 0){
                //set pick up button on click listener
                pickUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // switch to a fragment that display the request information and pick up button.
                        ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_pick_rider_up); }
                });}
            else{
                // switch to a fragment that display the request information and drop off button.
                dropOffButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_requests);
                        // move this request from curRequest to trip
                        //request parameters:
                        // String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc,
                        //                    String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                        //                    Car car, Double estimatedFare
                        //trip paras:
                        //String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                        //                Date dropOffTime, int duration, Car car, Double cost, Double rating
                        Request request2 = driver.getCurRequest();
                        Date current_time = new Date();
                        ArrayList<Trip> driver_trip_list = driver.getDriverTripList();
                        if(driver_trip_list == null){
                            driver_trip_list = new ArrayList<>();

                        }
                        driver_trip_list.add(new Trip(request2.getDriverEmail(),request2.getRiderEmail(), request2.getPickUpLoc(),request2.getDropOffLoc(),request2.getPickUpLocName(),request2.getDropOffLocName(),(Date)request2.getPickUpDateTime(),  (Date)current_time, (int)Math.abs(current_time.getTime()-request2.getPickUpDateTime().getTime()),request2.getCar(),request2.getEstimatedFare(),5.0));
                        driver.setDriverTripList(driver_trip_list);
                        driver.setCurRequest(null);
                        driverDatabaseAccessor.updateDriverProfile(driver,pickUpAndCurrentRequest.this);
                    }
                });
                // set emergency button on click listener
                emergencyCallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // call call number method to make a phone call
                        ((DriverMapActivity)getActivity()).callNumber("7806041057");//shway number
                    }
                });}

        }
        return view;
    }

    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver)
    {
        this.driver = driver;
        Request request = driver.getCurRequest();
        requestFromText.setText("From: " + request.getPickUpLocName());
        requestToText.setText("To: "+ request.getDropOffLocName());
        requestTimeText.setText("Time: "+ request.getPickUpDateTime());
        requestCostText.setText("Estimate Fare: "+request.getEstimatedFare());
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

}
