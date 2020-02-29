package com.example.hopinnow;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class EstimateFare {

    public EstimateFare(){}

    //estimates fair fare based on distance and time
    //Base Fare + ((Cost per minute x time of the ride)
    //      + (cost per mile x ride distance) x surge boost multiplier)
    //      + booking fee = Passengers Ride Fare.
    /**give attributes locations or google map object?*/
    public Double estimateFare(Location pickUpLoc, Location dropOffLoc, Date pickUpDateTime){
        Double price;
        Double baseFare = 1.5;
        Double costPerMinute = 0.16;
        Double costPerMile = 0.8;

        /**calls google map route calculation
         *      returns distance, time needed
         * distance, time = GOOGLE MAP METHOD that returns List<Double>
         * */

        //temporary, to be replaced by above
        Location pickUp = pickUpLoc;
        Location dropOff = dropOffLoc;
        Date dateTime = pickUpDateTime;
        Double distance = 1.0;
        Double time = 1.0; //= pickUpDateTime;


        price = baseFare + (costPerMinute * time) + (distance * costPerMile);

        return price;
    }
}
