package com.example.hopinnow.Entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public abstract class Ride {
    private Driver driver;
    private Rider rider;
    private LatLng pickUpLoc;
    private LatLng dropOffLoc;
    private String pickUpLocName;
    private String dropOffLocName;
    private Date pickUpDateTime;

    private Car car;

    public Ride(){}

    public Ride (Driver driver, Rider rider, LatLng pickUpLoc, LatLng dropOffLoc, String pickUpLocName, String dropOffLocName, Date pickUpDateTime, Car car){
        this.driver = driver;
        this.rider = rider;
        this.pickUpLoc = pickUpLoc;
        this.dropOffLoc = dropOffLoc;
        this.pickUpLocName = pickUpLocName;
        this.dropOffLocName = dropOffLocName;
        this.pickUpDateTime = pickUpDateTime;
        this.car = car;
    }

    //setters for all attributes
    public void setDriver(Driver driver){
        this.driver = driver;
    }

    public void setRider(Rider rider){
        this.rider = rider;
    }

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

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }
}
