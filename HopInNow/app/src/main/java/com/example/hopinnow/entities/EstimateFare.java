package com.example.hopinnow.entities;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.Date;



/**
 * Author: Tianyu Bai
 * Version: 1.0.0
 * calculate the estimated fare
 * To be finished
 */
public class EstimateFare {

    public EstimateFare(){}

    //estimates fair fare based on distance and time
    //Base Fare + (cost per mile x ride distance) + booking fee = Passengers Ride Fare.
    @SuppressLint("DefaultLocale")
    public Double estimateFare(LatLng pickUpLoc, LatLng dropOffLoc){
        Double price;
        Double baseFare = 2.5;
        Double costPerMile = 1.4;
        Double bookingFee = 1.0;

        //Manhattan distance is used here to replace Directs API (which is no longer free)
        Double horizontalDis = Math.abs(pickUpLoc.latitude-dropOffLoc.latitude);
        Double verticalDis = Math.abs(pickUpLoc.longitude-dropOffLoc.longitude);
        horizontalDis = horizontalDis * 111;
        verticalDis = verticalDis * 110.32 * Math.abs((Math.cos((pickUpLoc.latitude+dropOffLoc.latitude)/2)));


        Double distance = horizontalDis + verticalDis;

        price = Double.valueOf(String.format("%.2f",baseFare + (distance * costPerMile) + bookingFee));

        return price;
    }
}
