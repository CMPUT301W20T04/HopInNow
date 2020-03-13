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

    public void setCar(Car car) {
        try{
            this.car = car;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setDropOffLocName(String dropOffLocName) {
        try{
            this.dropOffLocName = dropOffLocName;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setPickUpLocName(String pickUpLocName) {
        try{
            this.pickUpLocName = pickUpLocName;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setDriverEmail(String driverEmail) {
        try{
            this.driverEmail = driverEmail;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setRiderEmail(String riderEmail) {
        try{
            this.riderEmail = riderEmail;
        }
        catch (Exception e){
            throw e;
        }
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
