package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Trip extends Ride {
    private Double cost;
    private Double rating;
    private Date dropOffDateTime;
    private int duration;

    public Trip(){}

    public Trip(String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                Date dropOffTime, int duration, Car car, Double cost, Double rating) {
        super(driver, rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, pickUpDateTime, car);
        this.cost = cost;
        this.rating = rating;
        this.dropOffDateTime = dropOffTime;
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setDropOffTime(Date dropOffTime) {
        this.dropOffDateTime = dropOffTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Date getDropOffTime() {
        return dropOffDateTime;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
