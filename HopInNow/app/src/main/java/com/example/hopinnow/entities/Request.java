package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import java.util.Date;

public class Request extends Ride {
    private Double estimatedFare;
    private String requestID;
    //public Request(Driver driver, Rider rider, Location pickUpLoc, Location dropOffLoc,
    // Date dateTime, Car car, Double estimatedFare){}

    // constructor
    public Request(){}

    public Request (String driver, String rider, LatLong pickUpLoc, LatLong dropOffLoc,
                    String pickUpLocName, String dropOffLocName, Date pickUpDateTime,
                    Car car, Double estimatedFare){
        super(driver,rider,pickUpLoc,dropOffLoc,pickUpLocName, dropOffLocName,pickUpDateTime,car);
        try{
            this.estimatedFare = estimatedFare;
        }
        catch(Exception e){
            throw e;
        }
    }

    //getter
    public Double getEstimatedFare() {
        if (estimatedFare == null){
            throw new NullPointerException();
        }
        else{
            return estimatedFare;
        }
    }

    //setter
    public void setEstimatedFare(Double estimatedFare) {
        try{
            this.estimatedFare = estimatedFare;
        }
        catch (Exception e){
            throw e;
        }
    }
    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
