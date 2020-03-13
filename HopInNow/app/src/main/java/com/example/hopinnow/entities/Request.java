package com.example.hopinnow.entities;

import com.example.hopinnow.helperclasses.LatLong;
import java.util.Date;

/**
 * request class which records the unfinished trip
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
public class Request extends Ride {
    private Double estimatedFare;
    private String requestID;
    //public Request(Driver driver, Rider rider, Location pickUpLoc, Location dropOffLoc,
    // Date dateTime, Car car, Double estimatedFare){}

    /**
     * empty constructor
     */
    public Request(){}

    /**
     * request constructor
     * @param driver
     * @param rider
     * @param pickUpLoc
     * @param dropOffLoc
     * @param pickUpLocName
     * @param dropOffLocName
     * @param pickUpDateTime
     * @param car
     * @param estimatedFare
     */
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

    /**
     * get estimated fare
     * @return
     */
    public Double getEstimatedFare() {
        if (estimatedFare == null){
            throw new NullPointerException();
        }
        else{
            return estimatedFare;
        }
    }

    /**
     * set the estimated fare
     * @param estimatedFare
     */
    public void setEstimatedFare(Double estimatedFare) {
        try{
            this.estimatedFare = estimatedFare;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get the reqeust ID
     * can be null
     * @return
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * set request ID
     * @param requestID
     */
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
