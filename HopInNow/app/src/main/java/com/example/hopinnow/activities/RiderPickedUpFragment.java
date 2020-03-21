package com.example.hopinnow.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
 * This class defines the fragment after rider is picked up by the driver.
 */
public class RiderPickedUpFragment extends Fragment {
    private Driver driver;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_pickedup, container, false);

        Request curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity()))
                .retrieveCurrentRequestLocal();
        driver = ((RiderMapActivity) Objects.requireNonNull(getActivity())).retrieveOfferedDriver();

        //mock case for ui testing
        if ((curRequest==null)||(driver==null)){
            Car car = new Car("Auburn","Speedster","Cream","111111");
            driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                    "12345678", true, 10.0,  null, car, null);
        }

        if (view != null) {

            //set driver name
            TextView driverName = view.findViewById(R.id.rider_pickedup_driver_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(v ->
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).showDriverInfo());

            //set drop off location
            TextView dropOffLoc = view.findViewById(R.id.rider_pickedup_dropOff);
            dropOffLoc.setText(curRequest.getDropOffLocName());

            //set estimated fare
            TextView estimatedFare = view.findViewById(R.id.rider_pickedup_fare);
            estimatedFare.setText(Double.toString(curRequest.getEstimatedFare()));

            // Click this button to call 911
            Button emergencyCallBtn = view.findViewById(R.id.rider_pickedup_emergency_button);
            emergencyCallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).
                            callNumber("911");
                }
            });

            //TODO actual switch triggered by driver confirming arriving destination
            Button nextBtn = view.findViewById(R.id.rider_pickedup_next_button);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //change fragment
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).
                            switchFragment(R.layout.fragment_rider_confirm_dropoff);
                }
            });
        }

        return view;
    }


}
