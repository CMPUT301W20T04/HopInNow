package com.example.hopinnow.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Entity class for ride.
 */
public abstract class Ride implements Serializable {
    private String driverEmail;
    private String riderEmail;
    private LatLong pickUpLoc;
    private LatLong dropOffLoc;
    private String pickUpLocName;
    private String dropOffLocName;
    private Date pickUpDateTime;
    private Car car;

    /**
     * Empty constructor to support database storage
     */
    Ride(){}

    /**
     * Constructor with all attributes as parameters to support dependency injection
     * @param driverEmail
     *      driver email
     * @param riderEmail
     *      rider email
     * @param pickUpLoc
     *      pickup location
     * @param dropOffLoc
     *      drop off location
     * @param pickUpLocName
     *      pickup location name
     * @param dropOffLocName
     *      drop off location name
     * @param pickUpDateTime
     *      the date and time the rider was picked up
     * @param car
     *      the car object of the driver
     */
    Ride(String driverEmail, String riderEmail, LatLong pickUpLoc, LatLong dropOffLoc,
         String pickUpLocName, String dropOffLocName, Date pickUpDateTime, Car car){
        this.driverEmail = driverEmail;
        this.riderEmail = riderEmail;
        this.pickUpLoc = pickUpLoc;
        this.dropOffLoc = dropOffLoc;
        this.pickUpLocName = pickUpLocName;
        this.dropOffLocName = dropOffLocName;
        this.pickUpDateTime = pickUpDateTime;
        this.car = car;
    }

    //setters for all attributes

    public void setPickUpLoc(LatLong pickUpLoc){
        this.pickUpLoc = pickUpLoc;
    }

    public void setDropOffLoc(LatLong dropOffLoc){
        this.dropOffLoc = dropOffLoc;
    }

    public void setPickUpDateTime(Date dateTime) {
        this.pickUpDateTime = dateTime;
    }

    /**
     * set car to new value
     * @param car
     *      car object of driver
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * set drop off location name to a new value
     * @param dropOffLocName
     *      drop off location name
     */
    public void setDropOffLocName(String dropOffLocName) {
        this.dropOffLocName = dropOffLocName;
    }

    /**
     * set pick up location name to a new value
     * @param pickUpLocName
     *      pick up location name
     */
    public void setPickUpLocName(String pickUpLocName) {
        this.pickUpLocName = pickUpLocName;
    }

    /**
     * set driver email to a new value
     * @param driverEmail
     *      driver's email
     */
    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    /**
     * set rider email to a new value
     * @param riderEmail
     *      rider email
     */
    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    //getters

    /**
     * return car object
     * @return car
     */
    public Car getCar() {
        return car;

    }

    /**
     * return pick up date time
     * @return pickUpDateTime
     */
    public Date getPickUpDateTime() {
        if (pickUpDateTime == null){
            throw new NullPointerException();
        }
        else{
            return pickUpDateTime;
        }
    }

    /**
     * return drop off location
     * @return dropOffLoc
     */
    public LatLong getDropOffLoc() {
        if (dropOffLoc == null){
            throw new NullPointerException();
        }
        else{
            return dropOffLoc;
        }
    }

    /**
     * return pick up location
     * @return pickUpLoc
     */
    public LatLong getPickUpLoc() {
        if (pickUpLoc == null){
            throw new NullPointerException();
        }
        else{
            return pickUpLoc;
        }
    }

    /**
     * return pick up location name
     * @return pickUpLocName
     */
    public String getPickUpLocName() {
        if (pickUpLocName == null){
            throw new NullPointerException();
        }
        else{
            return pickUpLocName;
        }
    }

    /**
     * return drop off location name
     * @return dropOffLocName
     */
    public String getDropOffLocName() {
        if (dropOffLocName == null){
            throw new NullPointerException();
        }
        else{
            return dropOffLocName;
        }
    }

    /**
     * return driver email
     * can be null for request initialization
     * @return driverEmail
     */
    public String getDriverEmail() {
        return driverEmail;
    }

    /**
     * return rider email
     * @return riderEmail
     */
    public String getRiderEmail() {
        if (riderEmail == null){
            throw new NullPointerException();
        }
        else{
            return riderEmail;
        }
    }
}
