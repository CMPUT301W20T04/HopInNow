package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Trip extends Ride {
    private Double cost;
    private Double rating;
    private Date dropOffDateTime;
    private Integer duration;

    public Trip(){}

    public Trip(String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                Date dropOffTime, int duration, Car car, Double cost, Double rating) {
        super(driver, rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, pickUpDateTime, car);
        try{
            this.cost = cost;
            this.rating = rating;
            this.dropOffDateTime = dropOffTime;
            this.duration = duration;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Double getCost() {
        if (cost == null){
            throw new NullPointerException();
        }
        else{
            return cost;
        }
    }

    public void setCost(Double cost) {
        try{
            this.cost = cost;
        }
        catch(Exception e){
            throw e;
        }
    }

    public void setDropOffTime(Date dropOffTime) {
        try{
            this.dropOffDateTime = dropOffTime;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setDuration(int duration) {
        try{
            this.duration = duration;
        }
        catch (Exception e){
            throw e;
        }
    }

    public int getDuration() {
        if (duration == null){
            throw new NullPointerException();
        }
        else{
            return duration;
        }
    }

    public Date getDropOffTime() {
        if (dropOffDateTime == null){
            throw new NullPointerException();
        }
        else{
            return dropOffDateTime;
        }
    }

    public Double getRating() {
        if (rating == null){
            throw new NullPointerException();
        }
        else{
            return rating;
        }
    }

    public void setRating(Double rating) {
        try{
            this.rating = rating;
        }
        catch (Exception e){
            throw e;
        }
    }
}
