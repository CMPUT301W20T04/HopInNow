package com.example.hopinnow.activitiesandfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopinnow.R;
import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class TripListActivity extends AppCompatActivity {
    ListView tripList;
    ArrayAdapter<Trip> tripAdapter;;
    ArrayList<Trip> tripDataList = new ArrayList<>();
    //remember to change the uml
    private LatLng edmonton = new LatLng(53.631611,-113.323975);
    private Driver driver1 = new Driver("123@qq.com","asdf","leon","123",true,null,null,null,null);
    private Rider rider1 = new Rider("321@qq.com","qwer","shway","3421",false,null,null);
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
        tripAdapter = new CustomTripList(this,tripDataList);
        tripList.setAdapter(tripAdapter);
        for(int i=0;i<10;i++){
            tripDataList.add(new Trip(driver1,rider1,edmonton,edmonton,"12","21", d1,d1,23,car1,23.5,5.0));}


        tripAdapter.notifyDataSetChanged();
        tripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),TripDetailActivity.class);
                //intent.putExtra("trip_index",position);
                //pass in a key
                intent.putExtra("trip_key", "21");
                //intent.putExtra("rider",trip.getRider().getClass());
                //intent.putExtra("pickup_loc",trip.getPickUpLoc());
                //intent.putExtra("dropoff_loc",trip.getDropOffLoc());

                startActivity(intent);
                //startActivityForResult(intent, 2);
            }
        });

    }


}