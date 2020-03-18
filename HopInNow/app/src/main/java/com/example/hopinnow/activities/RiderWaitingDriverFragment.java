package com.example.hopinnow.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Request;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Author: Franky He
 * This class defines the fragment while rider is waiting for driver offer.
 * This class is triggered by by rider creating a new current request.
 */
public class  RiderWaitingDriverFragment extends Fragment {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Chronometer chronometer;
    private boolean running;
    private Double lowest_price;
    private Double estimate_fare;
    private TextView fare_amount;
    private long savedTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rider_waiting_driver, container,
                false);
        Request curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveCurrentRequestLocal();
        lowest_price = curRequest.getEstimatedFare();
        estimate_fare = lowest_price;
        chronometer = view.findViewById(R.id.chronometer);

        fare_amount = view.findViewById(R.id.fare_amount);
        fare_amount.setText(df2.format(estimate_fare));

        startChronometer();

        Button add_money = view.findViewById(R.id.add_money);
        add_money.setOnClickListener(v -> addFare());

        Button reduce_money = view.findViewById(R.id.reduce_money);
        reduce_money.setOnClickListener(v -> reduceFare());

        Button cancel_request = view.findViewById(R.id.cancel_button);
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
        fare_amount.setText(df2.format(estimate_fare));
        //TODO UPDATE FIREBASE
    }

    private void reduceFare(){
        if(Double.parseDouble(df2.format(estimate_fare)) - 1 >= lowest_price) {
            estimate_fare -= 1;
            fare_amount.setText(df2.format(estimate_fare));
            //TODO UPDATE FIREBASE
        }
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

    private void endChronometer(){
        if(running){
            savedTime = chronometer.getBase();
            chronometer.stop();
            running = false;
        }
    }
}
