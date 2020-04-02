package com.example.hopinnow.entities;

import java.util.ArrayList;

/**
 * Author: Shuwei Wang
 * Version: 1.0.0
 * driver entity stores driver information, car, request, and trips
 */
public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Trip> driverTripList;
    private Double rating = 0.0;
    private int ratingCounts = 0;

    /**
     * Empty constructor
     */
    public Driver(){}

    /**
     * driver constructor
     * @param email
     *      email
     * @param password
     *      password
     * @param name
     *      name
     * @param phoneNumber
     *      phonenumber
     * @param deposit
     *      deposit
     * @param curRequest
     *      current request
     * @param car
     *      driver's car
     * @param driverTripList
     *      driver's trip history
     */
    public Driver(String email, String password, String name, String phoneNumber,
                  double deposit, Request curRequest, Car car, ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber, true, deposit);
        this.curRequest = curRequest;
        this.car = car;
        this.driverTripList = driverTripList;
        //first time
        this.rating = 0.00;
        this.ratingCounts = 0;
    }

    /**
     * get current request
     * can be null as database set needs to perform get
     * @return
     *      return the current request held by this driver:
     */
    public Request getCurRequest() {
        return curRequest;
    }

    /**
     * set current request
     * @param curRequest
     *      set the current request for this driver:
     */
    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }

    /**
     * get car information
     * can be null for request initialization
     * @return
     *      get the car information of this driver:
     */
    public Car getCar() {
        return this.car;
    }

    /**
     * set car information
     * @param car
     *      the car object to set
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * get driver
     * @return
     *      the current car object held by this class instance
     */
    public ArrayList<Trip> getDriverTripList() {
        return driverTripList;
    }

    /**
     * set the driver trip list
     * @param driverTripList
     *      set the driver's trip list:
     */
    public void setDriverTripList(ArrayList<Trip> driverTripList) {
        this.driverTripList = driverTripList;
    }

    /**
     * get the driver rating
     * @return
     *      get the rating of this driver:
     */
    public Double getRating() {
        return rating;
    }

    /**
     * set driver rating
     * @param rating
     *      set the rating of this driver:
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * get number of ratings
     * @return
     *      get the number of ratings of this driver:
     */
    public int getRatingCounts(){
        return ratingCounts;
    }

    /**
     * set the number of ratings
     * @param ratingCounts
     *      set the numbre of ratings of this driver:
     */
    public void setRatingCounts(int ratingCounts){
        this.ratingCounts = ratingCounts;
    }
}
