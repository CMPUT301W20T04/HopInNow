package com.example.hopinnow.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public abstract class Ride {
    private String driverEmail;
    private String riderEmail;
    private LatLng pickUpLoc;
    private LatLng dropOffLoc;
    private String pickUpLocName;
    private String dropOffLocName;
    private Date pickUpDateTime;
    private Car car;

    public Ride(){}

    public Ride (String driverEmail, String riderEmail, LatLng pickUpLoc, LatLng dropOffLoc,
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

    public void setPickUpLoc(LatLng pickUpLoc){
        this.pickUpLoc = pickUpLoc;
    }

    public void setDropOffLoc(LatLng dropOffLoc){
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

    //getters
    public Car getCar() {
        return car;
    }

    public Date getPickUpDateTime() {
        return pickUpDateTime;
    }

    public LatLng getDropOffLoc() {
        return dropOffLoc;
    }

    public LatLng getPickUpLoc() {
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

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }
//public abstract Request getCurRequest();
}
