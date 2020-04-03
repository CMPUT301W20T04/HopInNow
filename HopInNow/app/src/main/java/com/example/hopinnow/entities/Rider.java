package com.example.hopinnow.entities;

import java.util.ArrayList;

/**
 * Author: Shway Wang
 * Version: 1.0.0
 * Rider class extends User class
 */
public class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> riderTripList;


    /**
     * Empty constructor support database storage
     */
    public Rider() {}

    /**
     * Constructor supporting dependency injection
     * @param email
     *      email
     * @param password
     *      password
     * @param name
     *      name
     * @param phoneNumber
     *      phone number
     * @param userType
     *      user is either a rider or a driver
     * @param deposit
     *      deposit of rider or driver
     * @param curRequest
     *      current request
     * @param riderTripList
     *      rider's trip list
     */
    public Rider(String email, String password, String name, String phoneNumber,
                 boolean userType,double deposit,
                 Request curRequest, ArrayList<Trip> riderTripList) {
        super(email, password, name, phoneNumber, userType, deposit);
        this.curRequest = curRequest;
        this.riderTripList = riderTripList;
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
     *      current request
     */
    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
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
     *      the rider's trip list
     */
    public void setRiderTripList(ArrayList<Trip> riderTripList) {
        this.riderTripList = riderTripList;
    }
}
