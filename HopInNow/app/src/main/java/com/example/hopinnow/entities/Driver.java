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
    public Driver(String s, String s1, String lupin_the_third, String s2, boolean b, Object o, Car car, Object o1, Object o2) {}

    public Driver(String email, String password, String name, String phoneNumber, boolean userType,double deposit,
                  Request curRequest, Car car, ArrayList<Request> availableRequest,
                  ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber, userType, deposit);
        this.curRequest = curRequest;
        this.car = car;
        this.availableRequests = availableRequest;
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

    public ArrayList<Request> getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(ArrayList<Request> availableRequests) {
        this.availableRequests = availableRequests;
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
