package com.example.hopinnow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;

import java.util.Objects;

//TODO CALLED AFTER DRIVER CLICK COMPLETE TRIP

/**
 * Author: Tianyu Bai
 * This class defines the fragment that prompts rider on confirming his/her arrival at the
 * specified drop off location.
 *
 * todo : This event is to be triggered by driver confirming completion of the ride.
 */
public class RiderConfirmDropOffFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_confirm_dropoff, container,
                false);

        if (view != null) {

            // rider confirms, starts payment activity
            Button completeBtn = view.findViewById(R.id.rider_confirm_yes_button);
            completeBtn.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(),RiderPaymentActivity.class);
                startActivity(intent);
            });

            // rider does not confirm, go back to the last picked up ragment and notifies driver
            Button incompleteBtn = view.findViewById(R.id.rider_confirm_no_button);
            incompleteBtn.setOnClickListener(v -> {
                //TODO TOAST MESSAGE FOR DRIVER, DRIVER SIDE FRAGMENT GOES BACK TO PREVIOUS

                ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                        .switchFragment(R.layout.fragment_rider_pickedup);
            });

            // click this button to call 911
            Button emergencyCallBtn = view.findViewById(R.id.rider_confirm_emergency_button);
            emergencyCallBtn.setOnClickListener(v -> ((RiderMapActivity) Objects
                    .requireNonNull(getActivity()))
                    .callNumber("911"));
        }
        return view;
    }
}
