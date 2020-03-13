package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * ride class which defines all common functions of trips and requests
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
public abstract class Ride {
    private String driverEmail;
    private String riderEmail;
    private LatLong pickUpLoc;
    private LatLong dropOffLoc;
    private String pickUpLocName;
    private String dropOffLocName;
    private Date pickUpDateTime;
    private Car car;

    /**
     * empty constructor
     */
    public Ride(){}

    /**
     * ride constructor
     * @param driverEmail
     * @param riderEmail
     * @param pickUpLoc
     * @param dropOffLoc
     * @param pickUpLocName
     * @param dropOffLocName
     * @param pickUpDateTime
     * @param car
     */
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

    /**
     * set pick up location
     * @param pickUpLoc
     */
    public void setPickUpLoc(LatLong pickUpLoc){
        try{
            this.pickUpLoc = pickUpLoc;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set drop off location
     * @param dropOffLoc
     */
    public void setDropOffLoc(LatLong dropOffLoc){
        try{
            this.dropOffLoc = dropOffLoc;
        }
        catch(Exception e){
            throw e;
        }
    }

    /**
     * set pick up time
     * @param dateTime
     */
    public void setPickUpDateTime(Date dateTime) {
        try{
            this.pickUpDateTime = dateTime;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set car information
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
     * set drop off location name
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
     * set pick up location name
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
     * set driver email
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
     * set rider email
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

    /**
     * get the car information
     * @return
     */
    public Car getCar() {
        if (car == null){
            throw new NullPointerException();
        }
        else{
            return car;
        }
    }

    /**
     * get pick up time
     * @return
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
     * get drop off location
     * @return
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
     * get pick up location
     * @return
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
     * get pick up location name
     * @return
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
     * get drop off location name
     * @return
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
     * get driver email
     * @return
     */
    public String getDriverEmail() {
        if (driverEmail == null){
            throw new NullPointerException();
        }
        else{
            return driverEmail;
        }
    }

    /**
     * get rider email
     * @return
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
