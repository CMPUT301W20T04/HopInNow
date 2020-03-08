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

public class RiderConfirmDropoffFragment extends Fragment {
    Request curRequest;
    Driver driver;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_confirm_dropoff, container, false);

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, 10.0, null, car, null, null);
        //TODO set current Request
        curRequest = ((RiderMapActivity) getActivity()).retrieveCurrentRequest();

        // Get Fragment belonged Activity
        final FragmentActivity fragmentActivity = getActivity();

        if (view != null) {

            // Click this button to call driver
            Button completeBtn = view.findViewById(R.id.rider_confirm_yes_button);
            completeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO CHANGE INTENT TO PAYMENT
                }
            });

            // Click this button to email driver
            Button incompleteBtn = view.findViewById(R.id.rider_confirm_no_button);
            incompleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO TOAST MESSAGE FOR DRIVER, DRIVER SIDE FRAGMENT GOES BACK TO PREVIOUS

                    ((RiderMapActivity)getActivity()).switchFragment(R.layout.fragment_rider_pickedup);
                }
            });

            // Click this button to call 911
            Button emergencyCallBtn = view.findViewById(R.id.rider_confirm_emergency_button);
            emergencyCallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //change fragment
                    ((RiderMapActivity) getActivity()).callNumber("911");
                }
            });

        }

        return view;
    }
}
