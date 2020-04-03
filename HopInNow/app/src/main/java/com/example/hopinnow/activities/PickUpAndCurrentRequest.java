package com.example.hopinnow.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.hopinnow.R;
import com.example.hopinnow.database.DriverDatabaseAccessor;
import com.example.hopinnow.database.DriverRequestDatabaseAccessor;
import com.example.hopinnow.database.RiderDatabaseAccessor;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.statuslisteners.AvailRequestListListener;
import com.example.hopinnow.statuslisteners.DriverProfileStatusListener;
import com.example.hopinnow.statuslisteners.DriverRequestListener;
import com.example.hopinnow.statuslisteners.RequestAddDeleteListener;
import com.example.hopinnow.statuslisteners.RiderObjectRetrieveListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * Author: Qianxi Li
 * Co-author: Shway Wang
 * Version: 1.0.0
 * show the current request that driver has accepted
 */
public class PickUpAndCurrentRequest extends Fragment implements DriverProfileStatusListener,
        AvailRequestListListener, RequestAddDeleteListener, DriverRequestListener,
        RiderObjectRetrieveListener {
    private static final String TAG = "PickUpAndCurrentRequest";
    private Driver driver;
    private Rider rider;
    private Request request;
    private TextView requestTitleText;
    private TextView requestFromText;
    private TextView requestToText;
    private TextView requestTimeText;
    private TextView requestCostText;
    private TextView requestRiderText;
    private DriverRequestDatabaseAccessor driverRequestDatabaseAccessor;
    private DriverDatabaseAccessor driverDatabaseAccessor;
    private ProgressDialog progressDialog;
    private Context context;
    private Button pickUpButton;
    private Button dropOffButton;
    private Button emergencyCallButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.progressDialog = new ProgressDialog(this.getContext());
        this.progressDialog.setContentView(R.layout.custom_progress_bar);
        driverDatabaseAccessor = new DriverDatabaseAccessor();

        //here get the driver from database
        View view = inflater.inflate(R.layout.fragment_driver_pick_rider_up, container, false);
        if (view != null) {
            requestTitleText = view.findViewById(R.id.RequestInfoText);
            requestFromText = view.findViewById(R.id.requestFromText);
            requestToText = view.findViewById(R.id.requestToText);
            requestTimeText = view.findViewById(R.id.requestTimeText);
            requestCostText = view.findViewById(R.id.requestCostText);
            requestRiderText = view.findViewById(R.id.requestRiderText);
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
        RiderDatabaseAccessor rDA = new RiderDatabaseAccessor();
        rDA.getRiderObject(request.getRiderEmail(), this);
        requestFromText.setText(request.getPickUpLocName());
        requestToText.setText( request.getDropOffLocName());
        requestTimeText.setText(request.getPickUpDateTime().toString());
        requestCostText.setText(String.format(Locale.CANADA,
                "%.2f", request.getEstimatedFare()));

        requestRiderText.setOnClickListener(v -> showInfo());
        if (request==null){
            requestFromText.setText("From: pick up location ui test" );
            requestToText.setText("To: drop off location ui test");
            requestTimeText.setText("Time: right now ui test" );
            requestCostText.setText("Estimate Fare: fare ui test");
            return;
        }
        if (!request.isPickedUp()) {
            // the fragment that display the pickup button and request information
            pickUpButton.setVisibility(View.VISIBLE);
            dropOffButton.setVisibility(View.INVISIBLE);
            emergencyCallButton.setVisibility(View.INVISIBLE);
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

            });
        } else {
            // the fragment that display the drop off button and request information
            pickUpButton.setVisibility(View.INVISIBLE);
            dropOffButton.setVisibility(View.VISIBLE);
            emergencyCallButton.setVisibility(View.VISIBLE);
            // switch to a fragment that display the request information and drop off button.
            dropOffButton.setOnClickListener(v -> {
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
           
            });
            // set emergency button on click listener
            emergencyCallButton.setOnClickListener(v -> ((DriverMapActivity) Objects
                    .requireNonNull(getActivity())).
                    callNumber("0000911"));
        }
    }

    /**
     * Shows driver information and contact means on a dialog - Added By Viola
     */
    @SuppressLint({"CheckResult", "SetTextI18n"})
    private void showInfo() {

        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.setContentView(R.layout.dialog_driver_info);

        if (rider != null) {
            //set rider name
            TextView driverName = dialog.findViewById(R.id.dialog_driver_name);
            driverName.setText(rider.getName());

            //set driver specific information gone
            dialog.findViewById(R.id.dialog_driver_plate_label).setVisibility(View.GONE);
            dialog.findViewById(R.id.dialog_driver_car_label).setVisibility(View.GONE);
            TextView ra = dialog.findViewById(R.id.dialog_driver_rating);
            ra.setCompoundDrawables(null, null, null, null);
            ra.setText("Contact Rider By: ");
            ra.setTextSize(18);
            dialog.findViewById(R.id.dialog_driver_car).setVisibility(View.GONE);
            dialog.findViewById(R.id.dialog_driver_plate).setVisibility(View.GONE);

            //call rider
            Button callBtn = dialog.findViewById(R.id.dialog_call_button);
            callBtn.setOnClickListener(v -> callNumber(rider.getPhoneNumber()));

            //email rider
            Button emailBtn = dialog.findViewById(R.id.dialog_email_button);
            emailBtn.setOnClickListener(v -> emailDriver(rider.getEmail()));

            dialog.show();
        }
    }

    /**
     * Starts phone calling. -Added by Viola
     * @param phoneNumber
     *      the phone number to be called
     */
    @SuppressLint("CheckResult")
    private void callNumber(String phoneNumber){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rxPermissions = new RxPermissions(Objects.requireNonNull(getActivity()));
            rxPermissions
                    .request(Manifest.permission.CALL_PHONE)
                    .subscribe(granted -> {
                        if (granted) {
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(getActivity(),"User's Phone Number: " + phoneNumber,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            startActivity(callIntent);
        }
    }


    /*
    Stackoverflow post by Dira
    https://stackoverflow.com/questions/8701634/send-email-intent
    Answer by Dira (code from the question itself)
     */
    /**
     * Prompts email app selection and directs to email drafting page with auto0filled email address
     * of the driver. - Added by Viola
     * @param email
     *      the driver's email address
     */
    private void emailDriver(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        startActivity(Intent.createChooser(intent, "Send Email"));
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
    }

    @Override
    public void onDriverRequestCompleteFailure() {

    }

    @Override
    public void onWaitOnRatingSuccess(Request request) {

    }


    @Override
    public void onWaitOnRatingError() {

    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onRiderObjRetrieveSuccess(Rider rider) {
        this.rider = rider;
        this.requestRiderText.setText(rider.getName());
    }

    @Override
    public void onRiderObjRetrieveFailure() {

    }
}