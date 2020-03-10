package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Request> availableRequests;
    private ArrayList<Trip> driverTripList;
    private Double rating;
    private int ratingCounts;

    //constructor
    public Driver(){}
    public Driver(String email, String password, String name, String phoneNumber, boolean userType,
                  double deposit, Request curRequest, Car car, ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber, userType, deposit);
        this.curRequest = curRequest;
        this.car = car;
        this.driverTripList = driverTripList;
        //first time
        this.rating = 0.00;
        this.ratingCounts = 0;
    }
    // setters and getters
    public Request getCurRequest() {
        return curRequest;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ArrayList<Trip> getDriverTripList() {
        return driverTripList;
    }

    public void setDriverTripList(ArrayList<Trip> driverTripList) {
        this.driverTripList = driverTripList;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getRatingCounts(){ return ratingCounts;}

    public void setRatingCounts(int ratingCounts){this.ratingCounts = ratingCounts;}

}
