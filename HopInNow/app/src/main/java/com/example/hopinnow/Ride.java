package com.example.hopinnow;

import android.location.Location;

import java.sql.Time;
import java.util.Date;

public abstract class Ride {
    private Driver driver;
    private Rider rider;
    private Location pickUpLoc;
    private Location dropOffLoc;
    private Date pickUpDateTime;

    private Car car;

    public Ride(){}

    public Ride (Driver driver, Rider rider, Location pickUpLoc, Location dropOffLoc, Date pickUpDateTime, Car car){
        this.driver = driver;
        this.rider = rider;
        this.pickUpLoc = pickUpLoc;
        this.dropOffLoc = dropOffLoc;
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

    public void setPickUpLoc(Location pickUpLoc){
        this.pickUpLoc = pickUpLoc;
    }

    public void setDropOffLoc(Location dropOffLoc){
        this.dropOffLoc = dropOffLoc;
    }

    public void setPickUpDateTime(Date dateTime) {
        this.pickUpDateTime = dateTime;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    //getters
    public Car getCar() {
        return car;
    }

    public Date getPickUpDateTime() {
        return pickUpDateTime;
    }

    public Location getDropOffLoc() {
        return dropOffLoc;
    }

    public Location getPickUpLoc() {
        return pickUpLoc;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }
}
