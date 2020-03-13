package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public abstract class Ride {
    private String driverEmail;
    private String riderEmail;
    private LatLong pickUpLoc;
    private LatLong dropOffLoc;
    private String pickUpLocName;
    private String dropOffLocName;
    private Date pickUpDateTime;
    private Car car;

    public Ride(){}

    public Ride (String driverEmail, String riderEmail, LatLong pickUpLoc, LatLong dropOffLoc,
                 String pickUpLocName, String dropOffLocName, Date pickUpDateTime, Car car){
        try{
            this.driverEmail = driverEmail;
            this.riderEmail = riderEmail;
            this.pickUpLoc = pickUpLoc;
            this.dropOffLoc = dropOffLoc;
            this.pickUpLocName = pickUpLocName;
            this.dropOffLocName = dropOffLocName;
            this.pickUpDateTime = pickUpDateTime;
            this.car = car;
        }
        catch (Exception e){
            throw e;
        }
    }

    //setters for all attributes

    public void setPickUpLoc(LatLong pickUpLoc){
        try{
            this.pickUpLoc = pickUpLoc;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setDropOffLoc(LatLong dropOffLoc){
        try{
            this.dropOffLoc = dropOffLoc;
        }
        catch(Exception e){
            throw e;
        }
    }

    public void setPickUpDateTime(Date dateTime) {
        try{
            this.pickUpDateTime = dateTime;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set car to new value
     * @param car
     */
    public void setCar(Car car) {
        try{
            this.car = car;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set drop off location name to a new value
     * @param dropOffLocName
     */
    public void setDropOffLocName(String dropOffLocName) {
        try{
            this.dropOffLocName = dropOffLocName;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set pick up location name to a new value
     * @param pickUpLocName
     */
    public void setPickUpLocName(String pickUpLocName) {
        try{
            this.pickUpLocName = pickUpLocName;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set driver email to a new value
     * @param driverEmail
     */
    public void setDriverEmail(String driverEmail) {
        try{
            this.driverEmail = driverEmail;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set rider email to a new value
     * @param riderEmail
     */
    public void setRiderEmail(String riderEmail) {
        try{
            this.riderEmail = riderEmail;
        }
        catch (Exception e){
            throw e;
        }
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

//public abstract Request getCurRequest();
}
