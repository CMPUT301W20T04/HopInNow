package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Request> availableRequests;
    private ArrayList<Trip> driverTripList;
    private int rating;

    //constructor
    public Driver() {}
    public Driver(String email, String password, String name, String phoneNumber, boolean userType,
                  Request curRequest, Car car, ArrayList<Request> availableRequest,
                  ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber, userType);
        this.curRequest = curRequest;
        this.car = car;
        this.availableRequests = availableRequest;
        this.driverTripList = driverTripList;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
