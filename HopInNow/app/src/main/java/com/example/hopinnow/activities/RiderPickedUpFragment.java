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

public class RiderPickedUpFragment extends Fragment {
    private Driver driver;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_pickedup, container, false);

        //TODO assign driver
        Car car = new Car("Auburn", "Speedster", "Cream", "111111");
        driver = new Driver("111@gmail.com", "12345678", "Lupin the Third",
                "12345678", true, 10.0,  null, car, null, null);
        //TODO set current Request
        Request curRequest = ((RiderMapActivity) Objects.requireNonNull(getActivity())).retrieveCurrentRequest();

        if (view != null) {
            //set driver name
            TextView driverName = view.findViewById(R.id.rider_pickedup_driver_name);
            driverName.setText(driver.getName());
            driverName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDriverInfo();
                }
            });

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
                    //change fragment
                    ((RiderMapActivity) Objects.requireNonNull(getActivity())).
                            callNumber("911");
                }
            });

            //TODO TEMPORARY, actual event triggered by driver activity
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

    /**
     * Shows driver information and contact means in a dialog
     */
    private void showDriverInfo(){
        //change fragment
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.setContentView(R.layout.dialog_driver_info);

        //driver name
        TextView driverName= dialog.findViewById(R.id.dialog_driver_name);
        driverName.setText(driver.getName());

        //set driver rating
        TextView driverRating = dialog.findViewById(R.id.dialog_driver_rating);
        String rating;
        if (driver.getRating()== 0){
            rating = "not yet rated";
        } else {
            rating = Double.toString(driver.getRating());
        }
        driverRating.setText(rating);

        //set driver car
        TextView driverCar = dialog.findViewById(R.id.dialog_driver_car);
        String carInfo = driver.getCar().getColor()+" "+driver.getCar().getMake()+" "+driver.getCar().getModel();
        driverCar.setText(carInfo);

        //set driver license
        TextView driverLicense = dialog.findViewById(R.id.dialog_driver_plate);
        driverLicense.setText(driver.getCar().getPlateNumber());

        //phone the driver
        Button callBtn= dialog.findViewById(R.id.dialog_call_button);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiderMapActivity) Objects.requireNonNull(getActivity())).
                        callNumber(driver.getPhoneNumber());
            }
        });

        //email the driver
        Button emailBtn= dialog.findViewById(R.id.dialog_email_button);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiderMapActivity) Objects.requireNonNull(getActivity())).
                        callNumber(driver.getEmail());
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
