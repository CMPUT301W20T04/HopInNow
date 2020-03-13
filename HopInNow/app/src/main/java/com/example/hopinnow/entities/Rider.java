package com.example.hopinnow.entities;

import java.util.ArrayList;

/**
 * rider class which stores all the information of a rider
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
public class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> riderTripList;


    /**
     * empty constructor
     */
    public Rider() {}

    /**
     * rider constructor
     * @param email
     * @param password
     * @param name
     * @param phoneNumber
     * @param userType
     * @param deposit
     * @param curRequest
     * @param riderTripList
     */
    public Rider(String email, String password, String name, String phoneNumber,
                 boolean userType,double deposit,
                 Request curRequest, ArrayList<Trip> riderTripList) {
        super(email, password, name, phoneNumber, userType, deposit);
        try{
            this.curRequest = curRequest;
            this.riderTripList = riderTripList;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get current request
     * @return
     */
    public Request getCurRequest() {
        return curRequest;
    }

    /**
     * set current request
     * @param curRequest
     */
    public void setCurRequest(Request curRequest) {
        try{
            this.curRequest = curRequest;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get all history trips
     * @return
     */
    public ArrayList<Trip> getRiderTripList() {
        return riderTripList;
    }

    /**
     * set the trip list
     * @param riderTripList
     */
    public void setRiderTripList(ArrayList<Trip> riderTripList) {
        try{
            this.riderTripList = riderTripList;
        }
        catch (Exception e){
            throw e;
        }
    }
}
