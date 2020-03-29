package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;

import java.util.Objects;

/**
 * Author: Tianyu Bai
 * Editor: Shway Wang
 * This class defines the fragment that prompts rider's decision on the driver offer.
 *
 * todo: This class is to be triggered by driver sending an offer on rider's current request.
 * todo: accept and decline currently are disabled
 */
public class RiderDriverOfferFragment extends Fragment {
    private Request curRequest;
    private TextView driverName;
    private Button callBtn;
    private Button emailBtn;
    private Button acceptBtn;
    private Button declineBtn;
    private Driver driver;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_driver_offer, container
                ,false);

        curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveCurrentRequest();


        //TODO get curRequest from firebase, driver email is empty, means not accepted yet
        //curRequest
        //curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                //.retrieveCurrentRequest();

        //driver =  ((RiderMapActivity) Objects.requireNonNull(getActivity())).retrieveOfferedDriver();

        Car car = new Car("Auburn","Speedster","Cream","111111");
        final Driver driver = new Driver("111@gmail.com", "12345678",
                "Lupin the Third", "12345678", 10.0,  null,
                car, null);
        if(view!=null) {
            //set driver name
            this.driverName = view.findViewById(R.id.rider_driver_offer_name);
            this.driverName.setText(driver.getName());
            //set driver rating
            TextView driverRating = view.findViewById(R.id.rider_driver_offer_rating);
            String rating;
            if (this.driver.getRating()==0){
                rating = "not yet rated";
            } else {
                rating = Double.toString(driver.getRating());
            }
            driverRating.setText(rating);

            //set driver car
            TextView driverCar = view.findViewById(R.id.rider_driver_offer_car);
            String carInfo = this.driver.getCar().getColor() + " "
                    + this.driver.getCar().getMake() + " "
                    + this.driver.getCar().getModel();
            driverCar.setText(carInfo);
            //set driver license
            TextView driverLicense = view.findViewById(R.id.rider_driver_offer_plate);
            driverLicense.setText(driver.getCar().getPlateNumber());
            //call driver
            this.callBtn = view.findViewById(R.id.rider_offer_call_button);
            //email driver
            this.emailBtn = view.findViewById(R.id.rider_offer_email_button);
            // click this button to accept request
            this.acceptBtn = view.findViewById(R.id.rider_driver_offer_accept_button);
            // click this button to accept request
            this.declineBtn = view.findViewById(R.id.rider_driver_offer_decline_button);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.driverName.setOnClickListener(v -> {
            //shows driver information
            ((RiderMapActivity) Objects.requireNonNull(getActivity())).showDriverInfo();
        });
        this.callBtn.setOnClickListener(v -> ((RiderMapActivity) Objects
                .requireNonNull(getActivity()))
                .callNumber(driver.getPhoneNumber()));
        this.emailBtn.setOnClickListener(v -> ((RiderMapActivity) Objects
                .requireNonNull(getActivity()))
                .emailDriver(driver.getEmail()));
        this.acceptBtn.setOnClickListener(v -> {
            //TODO set rider.curRequest to have Boolean accept = true in firebase
            ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                    .saveCurrentRequestLocal(curRequest);
            ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                    .switchFragment(R.layout.fragment_rider_waiting_pickup);
        });
        this.declineBtn.setOnClickListener(v -> ((RiderMapActivity) Objects
                .requireNonNull(getActivity()))
                .switchFragment(-1));
    }
}
