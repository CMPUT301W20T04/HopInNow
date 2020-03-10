package com.example.hopinnow.entities;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Request extends Ride {
    private Double estimatedFare;

    //public Request(Driver driver, Rider rider, Location pickUpLoc, Location dropOffLoc, Date dateTime, Car car, Double estimatedFare){}

    // constructor
    public Request(){}

    public Request (String driver, String rider, LatLng pickUpLoc, LatLng dropOffLoc, String pickUpLocName, String dropOffLocName,  Date pickUpDateTime,
                    Car car, Double estimatedFare){
        super(driver,rider,pickUpLoc,dropOffLoc,pickUpLocName, dropOffLocName,pickUpDateTime,car);
        this.estimatedFare = estimatedFare;
    }

    //getter
    public Double getEstimatedFare() {
        return estimatedFare;
    }

    //setter
    public void setEstimatedFare(Double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

}
