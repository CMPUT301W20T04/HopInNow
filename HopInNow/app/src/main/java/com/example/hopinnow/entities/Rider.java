package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> riderTripList;


    //constructor
    public Rider() {}
    //constructor
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
    // setters and getters

    /**
     * return the current request of a rider
     * @return curRequest
     */
    public Request getCurRequest() {
        return curRequest;
    }

    /**
     * set a new request as current request
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
     * get rider trip list
     * @return riderTripList
     */
    public ArrayList<Trip> getRiderTripList() {
        return riderTripList;
    }

    /**
     * set rider Trip List to new value
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
