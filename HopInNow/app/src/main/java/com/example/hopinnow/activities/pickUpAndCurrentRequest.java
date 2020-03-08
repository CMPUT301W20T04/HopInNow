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
import com.example.hopinnow.database.RequestDatabaseAccessor;
import com.example.hopinnow.database.UserDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.User;
import com.example.hopinnow.statuslisteners.UserProfileStatusListener;

public class pickUpAndCurrentRequest extends Fragment implements UserProfileStatusListener{
    private Driver driver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();

        //here get the driver from database
        userDatabaseAccessor.getUserProfile(this);
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
            requestFromText.setText("From: "+driver.getAvailableRequests().get(0).getPickUpLocName());
            requestToText.setText("To: "+driver.getAvailableRequests().get(0).getDropOffLocName());
            requestTimeText.setText("Time: "+driver.getAvailableRequests().get(0).getPickUpDateTime());
            requestCostText.setText("Estimate Fare: "+driver.getAvailableRequests().get(0).getEstimatedFare());
            pickUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_pick_rider_up);
                }
            });
            Button dropOffButton = view.findViewById(R.id.dropOffRiderButton);
            dropOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_requests);
                }
            });
            Button emergencyCallButton = view.findViewById(R.id.EmergencyCall);
            emergencyCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DriverMapActivity)getActivity()).callNumber("7806041057");//shway number
                }
            });




        }









        return super.onCreateView(inflater, container, savedInstanceState);






    }

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

    }
}
