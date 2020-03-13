package com.example.hopinnow.entities;

import java.util.ArrayList;

/**
 * driver entity stores driver information, car, request, and trips
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Trip> driverTripList;
    private Double rating;
    private Integer ratingCounts;

    /**
     * Empty constructor
     */
    public Driver(){}

    /**
     * driver constructor
     * @param email
     * @param password
     * @param name
     * @param phoneNumber
     * @param userType
     * @param deposit
     * @param curRequest
     * @param car
     * @param driverTripList
     */
    public Driver(String email, String password, String name, String phoneNumber, boolean userType,
                  double deposit, Request curRequest, Car car, ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber, userType, deposit);
        try{
            this.curRequest = curRequest;
            this.car = car;
            this.driverTripList = driverTripList;
            //first time
            this.rating = 0.00;
            this.ratingCounts = 0;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get current request
     * can be null as database set needs to perform get
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
     * get car information
     * can be null for request initialization
     * @return
     */
    public Car getCar() {
        return car;
    }

    /**
     * set car information
     * @param car
     */
    public void setCar(Car car) {
        try{
            this.car = car;
        }
        catch(Exception e){
            throw e;
        }
    }

    /**
     * get driver
     * @return
     */
    public ArrayList<Trip> getDriverTripList() {
        if (driverTripList == null){
            throw new NullPointerException();
        }
        else{
            return driverTripList;
        }
    }

    /**
     * set the driver trip list
     * @param driverTripList
     */
    public void setDriverTripList(ArrayList<Trip> driverTripList) {
        try{
            this.driverTripList = driverTripList;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get the driver rating
     * @return
     */
    public Double getRating() {
        if (rating == null){
            throw new NullPointerException();
        }
        else{
            return rating;
        }
    }

    /**
     * set driver rating
     * @param rating
     */
    public void setRating(Double rating) {
        try{
            this.rating = rating;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get number of ratings
     * @return
     */
    public int getRatingCounts(){
        if (ratingCounts == null){
            throw new NullPointerException();
        }
        else{
            return ratingCounts;
        }
    }

    /**
     * set the number of ratings
     * @param ratingCounts
     */
    public void setRatingCounts(int ratingCounts){
        try{
            this.ratingCounts = ratingCounts;
        }
        catch(Exception e){
            throw e;
        }
    }

}
