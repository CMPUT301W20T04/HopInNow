package com.example.hopinnow.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;

import java.text.DecimalFormat;

public class RiderWaitingDriverFragment extends Fragment {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public Chronometer chronometer;
    public boolean running;
    public Double estimate_fare = 2.68;
    public Double lowest_price = estimate_fare;
    TextView fare_amount;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_rider_waiting_driver, container, false);
        chronometer = view.findViewById(R.id.chronometer);
        final FragmentActivity fragmentActivity = getActivity();


        if(view != null){
            fare_amount = view.findViewById(R.id.fare_amount);
            fare_amount.setText(df2.format(estimate_fare));

        }
        startChronometer();

        Button add_money= view.findViewById(R.id.add_money);
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFare();
            }
        });

        Button reduce_money= view.findViewById(R.id.reduce_money);
        reduce_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceFare();
            }
        });

        Button cancel_request= view.findViewById(R.id.cancel_button);
        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiderMapActivity) getActivity()).cancelRequest();
                endChronometer();
            }
        });


        return view;
    }

    public void addFare(){

        estimate_fare += 1;
        fare_amount.setText(df2.format(estimate_fare));
    }

    public void reduceFare(){
        if(Double.valueOf(df2.format(estimate_fare)) - 1 >= lowest_price) {
            estimate_fare -= 1;
            fare_amount.setText(df2.format(estimate_fare));
        }
    }

    public void startChronometer(){
        if(!running){
            chronometer.start();
            running = true;
        }
    }

    public void endChronometer(){
        if(running){
            chronometer.stop();
            running = false;
        }
    }
}
