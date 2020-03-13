package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * trip class extends ride which stands for all finished trips
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
public class Trip extends Ride {
    private Double cost;
    private Double rating;
    private Date dropOffDateTime;
    private Integer duration;

    /**
     * empty constructor
     */
    public Trip(){}

    /**
     * trip constructor
     * @param driver
     * @param rider
     * @param pickUpLoc
     * @param dropOffLoc
     * @param pickUpLocName
     * @param dropOffLocName
     * @param pickUpDateTime
     * @param dropOffTime
     * @param duration
     * @param car
     * @param cost
     * @param rating
     */
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
     * get cost
     * @return
     */
    public Double getCost() {
        if (cost == null){
            throw new NullPointerException();
        }
        else{
            return cost;
        }
    }

    /**
     * set cost
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
     * set drop off time
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
     * set trip duration
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
     * get trip duration
     * @return
     */
    public int getDuration() {
        if (duration == null){
            throw new NullPointerException();
        }
        else{
            return duration;
        }
    }

    /**
     * get drop off time
     * @return
     */
    public Date getDropOffTime() {
        if (dropOffDateTime == null){
            throw new NullPointerException();
        }
        else{
            return dropOffDateTime;
        }
    }

    /**
     * get trip rating
     * @return
     */
    public Double getRating() {
        if (rating == null){
            throw new NullPointerException();
        }
        else{
            return rating;
        }
    }

    /**
     * set trip rating
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
