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

    /**
     * return the cost of the trip
     * @return cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * set the cost to a new value
     * @param cost
     */
    public void setCost(Double cost) {
        try{
            this.cost = cost;
        }
        catch(Exception e){
            throw e;
        }
    }

    /**
     * set the drop off time to a new value
     * @param dropOffTime
     */
    public void setDropOffTime(Date dropOffTime) {
        try{
            this.dropOffDateTime = dropOffTime;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set the duration to a new value
     * @param duration
     */
    public void setDuration(int duration) {
        try{
            this.duration = duration;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get the duration of a trip
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * get drop off time
     * @return dropOffDateTime
     */
    public Date getDropOffTime() {
        return dropOffDateTime;
    }

    /**
     * get the rating of the trip
     * @return rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * set the rating to a new value
     * @param rating
     */
    public void setRating(Double rating) {
        try{
            this.rating = rating;
        }
        catch (Exception e){
            throw e;
        }
    }
}
