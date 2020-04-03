package com.example.hopinnow.entities;

import java.util.Date;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Trip class extends Ride
 */
public class Trip extends Ride {
    private Double cost;
    private Double rating;
    private Date dropOffDateTime;
    private Integer duration;

    /**
     * Empty constructor to support database storage
     */
    public Trip(){}

    /**
     * Constructor supporting dependency injection
     * @param driver
     *      driver
     * @param rider
     *      rider
     * @param pickUpLoc
     *      pickup location
     * @param dropOffLoc
     *      drop off location
     * @param pickUpLocName
     *      pickup location name
     * @param dropOffLocName
     *      drop off location name
     * @param pickUpDateTime
     *      pickup date time
     * @param dropOffTime
     *      drop off time
     * @param duration
     *      duration of ride
     * @param car
     *      car of driver
     * @param cost
     *      cost of travel
     * @param rating
     *      rating of experience
     */
    public Trip(String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                Date dropOffTime, int duration, Car car, Double cost, Double rating) {
        super(driver, rider, pickUpLoc, dropOffLoc, pickUpLocName, dropOffLocName, pickUpDateTime, car);
        this.cost = cost;
        this.rating = rating;
        this.dropOffDateTime = dropOffTime;
        this.duration = duration;
    }

    /**
     * return the cost of the trip
     * @return cost
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
     * set the cost to a new value
     * @param cost
     *      cost of ride
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * set the drop off time to a new value
     * @param dropOffTime
     *      drop off time
     */
    public void setDropOffTime(Date dropOffTime) {
        this.dropOffDateTime = dropOffTime;
    }

    /**
     * set the duration to a new value
     * @param duration
     *      duration of ride
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * get the duration of a trip
     * @return duration
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
     */
    public void getDropOffTime() {
        if (dropOffDateTime == null){
            throw new NullPointerException();
        }
    }

    /**
     * get the rating of the trip
     * @return rating
     */
    public Double getRating() {
        if (rating == null){
            return 0.00;
        }
        else{
            return rating;
        }
    }

    /**
     * set the rating to a new value
     * @param rating
     *      rating of driver
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }
}
