package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Author: Franky He
 * This class defines the fragment while rider is waiting for driver offer.
 */
public class  RiderWaitingDriverFragment extends Fragment {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Chronometer chronometer;
    private boolean running;
    private double lowest_price;
    private double estimate_fare;
    private TextView fare_amount;
    private long savedTime = 0;

    public RiderWaitingDriverFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rider_waiting_driver, container,
                false);
        lowest_price = (Double) Objects.requireNonNull(getArguments())
                .getSerializable("baseFare");
        Request curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveCurrentRequestLocal();
        Rider rider = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveRider();
        estimate_fare = curRequest.getEstimatedFare();
        chronometer = view.findViewById(R.id.chronometer);

        fare_amount = view.findViewById(R.id.fare_amount);
        fare_amount.setText(df2.format(estimate_fare));

        startChronometer();

        Button add_money = view.findViewById(R.id.add_money);
        add_money.setOnClickListener(v -> {
            if ((estimate_fare+1) <= rider.getDeposit()) {
                addFare();
            } else {
                Toast.makeText(Objects.requireNonNull(getActivity()),
                        "Sorry, you do not have enough deposit to add money.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button reduce_money = view.findViewById(R.id.reduce_money);
        reduce_money.setOnClickListener(v -> {
            if((estimate_fare - 1) >= lowest_price) {
                reduceFare();
            } else {
                Toast.makeText(((RiderMapActivity) Objects.requireNonNull(getActivity())),
                        "Sorry, you can not go lower than the estimated base fare.",
                        Toast.LENGTH_SHORT).show();
            }

        });
        // this is the cancel request button:
        Button cancel_request = view.findViewById(R.id.cancel_button);
        // the action after the cancel request button is clicked:
        cancel_request.setOnClickListener(v -> {
            ((RiderMapActivity) Objects.requireNonNull(getActivity())).cancelRequestLocal();
            endChronometer();
        });


        //temporary for linking fragments
        Button next = view.findViewById(R.id.rider_waiting_driver_next);
        next.setOnClickListener(v -> {
            ((RiderMapActivity)getActivity()).switchFragment(R.layout.fragment_rider_driver_offer);
            endChronometer();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startChronometer();
    }


    private void addFare(){
        estimate_fare += 1;
        estimate_fare = (double) Math.round(estimate_fare*100)/100;
        fare_amount.setText(df2.format(estimate_fare));
        ((RiderMapActivity) Objects.requireNonNull(getActivity())).updateFare(estimate_fare);
    }

    private void reduceFare(){
        estimate_fare -= 1;
        estimate_fare = (double) Math.round(estimate_fare*100)/100;
        fare_amount.setText(df2.format(estimate_fare));
        ((RiderMapActivity) Objects.requireNonNull(getActivity())).updateFare(estimate_fare);
    }

    private void startChronometer(){
        if(!running){
            if (savedTime!=0){
                chronometer.setBase(savedTime);
            }
            chronometer.start();
            running = true;
        }
    }

    public void endChronometer(){
        if(running){
            savedTime = chronometer.getBase();
            chronometer.stop();
            running = false;
        }
    }
}
