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

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDropOffLocName(String dropOffLocName) {
        this.dropOffLocName = dropOffLocName;
    }

    public void setPickUpLocName(String pickUpLocName) {
        this.pickUpLocName = pickUpLocName;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    //getters
    public Car getCar() {
        return car;
    }

    public Date getPickUpDateTime() {
        return pickUpDateTime;
    }

    public LatLong getDropOffLoc() {
        return dropOffLoc;
    }

    public LatLong getPickUpLoc() {
        return pickUpLoc;
    }

    public String getPickUpLocName() {
        return pickUpLocName;
    }

    public String getDropOffLocName() {
        return dropOffLocName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

//public abstract Request getCurRequest();
}
