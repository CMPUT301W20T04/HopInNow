package com.example.hopinnow.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.DriverRequestDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.LatLong;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.helperclasses.ProgressbarDialog;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.example.hopinnow.statuslisteners.RequestAddDeleteListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Author: Qianxi Li
 * Co-author: Shway Wang
 * Version: 1.0.0
 * show the current request that driver has accepted
 */
public class PickUpAndCurrentRequest extends Fragment implements DriverProfileStatusListener,
        AvailRequestListListener, RequestAddDeleteListener, DriverRequestListener {
    public static final String TAG = "PickUpAndCurrentRequest";
    private Driver driver;
    private Request request;
    private TextView requestTitleText;
    private TextView requestFromText;
    private TextView requestToText;
    private TextView requestTimeText;
    private TextView requestCostText;
    private DriverRequestDatabaseAccessor driverRequestDatabaseAccessor;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    // Shway Wang added this:
    private ProgressbarDialog progressbarDialog;
    private Context context;
    Button pickUpButton;
    Button dropOffButton;
    Button emergencyCallButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.progressbarDialog = new ProgressbarDialog(this.getContext());
        driverDatabaseAccessor = new DriverDatabaseAccessor();
        int display_mode;
        //here get the driver from database


        View view = inflater.inflate(R.layout.fragment_driver_pick_rider_up, container, false);
        if (view != null) {
            requestTitleText = view.findViewById(R.id.RequestInfoText);
            requestFromText = view.findViewById(R.id.requestFromText);
            requestToText = view.findViewById(R.id.requestToText);
            requestTimeText = view.findViewById(R.id.requestTimeText);
            requestCostText = view.findViewById(R.id.requestCostText);
            pickUpButton = view.findViewById(R.id.PickUpRiderButton);
            dropOffButton = view.findViewById(R.id.dropOffRiderButton);
            emergencyCallButton = view.findViewById(R.id.EmergencyCall);
            // here call getDriverProfile method
            driverDatabaseAccessor.getDriverProfile(this);
        }
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDriverProfileRetrieveSuccess(Driver driver) {
        this.driver = driver;
        request = driver.getCurRequest();
        requestFromText.setText(request.getPickUpLocName());
        requestToText.setText( request.getDropOffLocName());
        requestTimeText.setText(request.getPickUpDateTime().toString());
        requestCostText.setText(String.format(Locale.CANADA,
                "%.2f", request.getEstimatedFare()));
        if (request==null){
            requestFromText.setText("From: pick up location ui test" );
            requestToText.setText("To: drop off location ui test");
            requestTimeText.setText("Time: right now ui test" );
            requestCostText.setText("Estimate Fare: fare ui test");
            return;
        }
        //display_mode = ((DriverMapActivity)getActivity()).getCurrentRequestPageCounter();
        if (!request.isPickedUp()) {
            // the fragment that display the pickup button and request information
            pickUpButton.setVisibility(View.VISIBLE);
            dropOffButton.setVisibility(View.INVISIBLE);
            emergencyCallButton.setVisibility(View.INVISIBLE);
            //((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(1);
            driverRequestDatabaseAccessor = new DriverRequestDatabaseAccessor();
            driverRequestDatabaseAccessor.driverListenOnCancelRequestBeforeArrive(request,this);
            //set pick up button on click listener
            pickUpButton.setOnClickListener(v -> {
                // switch to a fragment that display the request information and pick up button.
                String rider_email = driver.getCurRequest().getRiderEmail();

                request.setPickedUp(true);
                driver.setCurRequest(request);
                driverRequestDatabaseAccessor.driverPickupRider(request,
                        PickUpAndCurrentRequest.this);
                //   ((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_pick_rider_up);

            });
        } else {
            // the fragment that display the drop off button and request information
            pickUpButton.setVisibility(View.INVISIBLE);
            dropOffButton.setVisibility(View.VISIBLE);
            emergencyCallButton.setVisibility(View.VISIBLE);
            //((DriverMapActivity)getActivity()).setCurrentRequestPageCounter(0);
            // switch to a fragment that display the request information and drop off button.
            dropOffButton.setOnClickListener(v -> {
                //((DriverMapActivity)getActivity()).switchFragment(R.layout.fragment_driver_requests);
                // move this request from curRequest to trip
                //request parameters:
                // String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc,
                //                    String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                //                    Car car, Double estimatedFare
                //trip paras:
                //String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                //                Date dropOffTime, int duration, Car car, Double cost, Double rating
                request.setArrivedAtDest(true);
                driverRequestDatabaseAccessor = new DriverRequestDatabaseAccessor();
                driverRequestDatabaseAccessor.driverDropoffRider(request, PickUpAndCurrentRequest.this);
                Intent intent = new Intent(
                        (Objects.requireNonNull(getActivity())).getApplicationContext(),
                        DriverScanPaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Driver", driver);
                intent.putExtras(bundle);
                startActivity(intent);
                // moved to onDriverDropoffSuccess
                    /*Intent intent = new Intent((getActivity()).getApplicationContext(), DriverScanPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Driver", driver);
                    intent.putExtras(bundle);
                    startActivity(intent);*/

                    /*
                    Request request2 = driver.getCurRequest();
                    Date current_time = new Date();
                    driverRequestDatabaseAccessor = new DriverRequestDatabaseAccessor();
                    ArrayList<Trip> driverTripList = driver.getDriverTripList();
                    if(driverTripList == null){
                        driverTripList = new ArrayList<>();

                    }
                    driverTripList.add(new Trip(request2.getDriverEmail(),request2.getRiderEmail(),
                            request2.getPickUpLoc(),request2.getDropOffLoc(),
                            request2.getPickUpLocName(),request2.getDropOffLocName(),
                            (Date)request2.getPickUpDateTime(),  (Date)current_time,
                            (int)Math.abs(current_time.getTime() -
                                    request2.getPickUpDateTime().getTime()),
                            request2.getCar(),request2.getEstimatedFare(),5.0));
                    driver.setDriverTripList(driverTripList);

                    driverRequestDatabaseAccessor.driverCompleteRequest(request2,
                            PickUpAndCurrentRequest.this);*/
            });
            // set emergency button on click listener
            emergencyCallButton.setOnClickListener(v -> {
                // call call number method to make a phone call
                ((DriverMapActivity) getActivity())
                        .callNumber("7806041057");//shway number
            });
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDriverProfileRetrieveFailure() {

    }

    @Override
    public void onDriverProfileUpdateSuccess(Driver driver) {

    }

    @Override
    public void onDriverProfileUpdateFailure() {

    }

    @Override
    public void onRequestAddedSuccess() {

    }

    @Override
    public void onRequestAddedFailure() {

    }

    @Override
    public void onRequestDeleteSuccess() {
    }

    @Override
    public void onRequestDeleteFailure() {

    }

    @Override
    public void onGetRequiredRequestsSuccess(ArrayList<Request> requests) {
        this.driverDatabaseAccessor.updateDriverProfile(driver, PickUpAndCurrentRequest.this);
    }

    @Override
    public void onGetRequiredRequestsFailure() {

    }

    @Override
    public void onAllRequestsUpdateSuccess(ArrayList<Request> requests) {

    }

    @Override
    public void onAllRequestsUpdateError() {

    }

    @Override
    public void onDriverRequestAccept() {

    }

    @Override
    public void onDriverRequestTimeoutOrFail() {

    }

    @Override
    public void onRequestAlreadyTaken() {

    }

    @Override
    public void onRequestInfoChange(Request request) {

    }

    @Override
    public void onRequestAcceptedByRider(Request request) {
        //driverRequestDatabaseAccessor.driverListenOnRequestBeforeArrive(request,this);
    }

    @Override
    public void onRequestDeclinedByRider() {
        ((DriverMapActivity) Objects.requireNonNull(context)).switchFragment(-1);
    }

    @Override
    public void onDriverPickupSuccess() {
        Toast.makeText(context,"Request is cancelled by " +
                "rider. Please find a new request.", Toast.LENGTH_SHORT).show();
        driverDatabaseAccessor.updateDriverProfile(driver,this);
        ((DriverMapActivity) Objects.requireNonNull(context))
                .switchFragment(R.layout.fragment_driver_pick_rider_up);
    }

    @Override
    public void onDriverPickupFail() {

    }

    @Override
    public void onDriverDropoffSuccess(Request request) {
    }

    @Override
    public void onDriverDropoffFail() {

    }

    @Override
    public void onDriverRequestCompleteSuccess() {
        driver.setCurRequest(null);

        //this.driverRequestDatabaseAccessor.getAllRequest(new LatLong(), this);

    }

    @Override
    public void onDriverRequestCompleteFailure() {

    }

    @Override
    public void onWaitOnRatingSuccess() {

    }

    @Override
    public void onWaitOnRatingError() {

    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}