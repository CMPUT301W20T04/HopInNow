package com.example.hopinnow.activities;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.statuslisteners.RiderProfileStatusListener;
import com.example.hopinnow.statuslisteners.RiderRequestAcceptedListener;

import java.util.Objects;
import com.example.hopinnow.database.UserDatabaseAccessor;

/**
 * Author: Tianyu Bai
 * This class defines the fragment that prompts rider's decision on the driver offer.
 *
 * todo: This class is to be triggered by driver sending an offer on rider's current request.
 */
public class RiderDriverOfferFragment extends Fragment {
    private Request curRequest;
    private Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_driver_offer, container
                ,false);

        //curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                //.retrieveCurrentRequestOnline();
        //driver = curRequest.getDriver();


        //TODO get curRequest from firebase, acceptance boolean is false
        //curRequest
        Car car = new Car("Auburn","Speedster","Cream","111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, 10.0,  null, car,
                null);
        curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveCurrentRequestLocal();




        if(view!=null) {

            //set driver name
            TextView driverName = view.findViewById(R.id.rider_driver_offer_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //shows driver information
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).showDriverInfo(driver);
                }
            });

            //set driver rating
            TextView driverRating = view.findViewById(R.id.rider_driver_offer_rating);
            String rating;
            if (driver.getRating()==0){
                rating = "not yet rated";
            } else {
                rating = Double.toString(driver.getRating());
            }
            driverRating.setText(rating);

            //set driver car
            TextView driverCar = view.findViewById(R.id.rider_driver_offer_car);
            String carInfo = driver.getCar().getColor() + " " + driver.getCar().getMake() + " "
                    + driver.getCar().getModel();
            driverCar.setText(carInfo);

            //set driver license
            TextView driverLicense = view.findViewById(R.id.rider_driver_offer_plate);
            driverLicense.setText(driver.getCar().getPlateNumber());

            //call driver
            Button callBtn= view.findViewById(R.id.rider_offer_call_button);
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                            .callNumber(driver.getPhoneNumber());
                }
            });

            //email driver
            Button emailBtn= view.findViewById(R.id.rider_offer_email_button);
            emailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                            .emailDriver(driver.getEmail());
                }
            });

            // click this button to accept request
            Button acceptBtn = view.findViewById(R.id.rider_driver_offer_accept_button);
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO set rider.curRequest to have Boolean accept = true in firebase
                    ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                            .saveCurrentRequestLocal(curRequest);
                    ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                            .switchFragment(R.layout.fragment_rider_waiting_pickup);
                }
            });

            // click this button to accept request
            Button declineBtn = view.findViewById(R.id.rider_driver_offer_decline_button);
            declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO POPBACKSTACK?
                    ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                            .switchFragment(-1);
                }
            });
        }

        return view;
    }



}
