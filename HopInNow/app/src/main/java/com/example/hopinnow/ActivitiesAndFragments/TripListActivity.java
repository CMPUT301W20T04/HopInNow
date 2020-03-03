package com.example.hopinnow.ActivitiesAndFragments;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class TripListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView tripList;
    ArrayAdapter<Trip> tripAdapter;;
    ArrayList<Trip> tripDataList = new ArrayList<>();
    //remember to change the uml
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private Driver driver1 = new Driver("123@qq.com","asdf","leon","123",null,null,null,null);
    private Rider rider1 = new Rider("321@qq.com","qwer","shway","3421",null,null);
    private Car car1 = new Car("1","1","1","1");
    Date d1 = new Date();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripList = findViewById(R.id.trip_list);
        //load trips from database here
        //Trip:
        //Driver driver, Rider rider, LatLng pickUpLoc, LatLng dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
        //                Date dropOffTime, int duration, Car car, Double cost, Double rating
        //driver:
        //String email, String password, String name, String phoneNumber, Request curRequest,
        //                  Car car, ArrayList<Request> availableRequest, ArrayList<Trip> driverTripList
        //rider:
        //String email, String password, String name, String phoneNumber,
        // Request curRequest, ArrayList<Trip> riderTripList

        for(int i=0;i<10;i++){
            tripDataList.add(new Trip(driver1,rider1,edmonton,edmonton,"12","21", d1,d1,23,car1,23.5,5.0));}

        tripAdapter = new CustomTripList(this,tripDataList);
        tripList.setAdapter(tripAdapter);
        tripList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
