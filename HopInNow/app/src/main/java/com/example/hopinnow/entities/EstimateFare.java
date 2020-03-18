package com.example.hopinnow.entities;

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
    /**give attributes locations or google map object?*/
    public Double estimateFare(LatLng pickUpLoc, LatLng dropOffLoc){
        Double price;
        Double baseFare = 2.0;
        Double costPerMile = 0.95;
        Double bookingFee = 1.0;
        Double earthRadius = 6371.0; //in km

        //temporary, to be replaced by above
        LatLng pickUp = pickUpLoc;
        LatLng dropOff = dropOffLoc;
        Double horizontalDis = Math.abs(pickUp.latitude-dropOff.latitude);
        Double verticalDis = Math.abs(dropOff.longitude-dropOff.longitude);
        horizontalDis = horizontalDis * 110.574;
        verticalDis = verticalDis * earthRadius * Math.cos((pickUp.latitude+dropOff.latitude)/2);


        Double distance = horizontalDis + verticalDis;


        price = Double.parseDouble(new DecimalFormat("##.##")
                .format(baseFare + (distance * costPerMile) + bookingFee));

        return price;
    }
}
