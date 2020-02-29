package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Request> availableRequests;
    private ArrayList<Trip> driverTripList;

    //constructor
    public Driver(String email, String password, String name, String phoneNumber, Request curRequest,
                  Car car, ArrayList<Request> availableRequest, ArrayList<Trip> driverTripList) {
        super(email, password, name, phoneNumber);
        this.curRequest = curRequest;
        this.car = car;
        this.availableRequests = availableRequest;
        this.driverTripList = driverTripList;
    }

    //getter
    public Request getCurRequest() {
        return curRequest;
    }

    public Car getCar() {
        return car;
    }

    public ArrayList<Request> getAvailableRequest() {
        return availableRequests;
    }

    public ArrayList<Trip> getDriverTripList() {
        return driverTripList;
    }

    //setter
    public void setDriverTripList(ArrayList<Trip> driverTripList) {
        this.driverTripList = driverTripList;
    }

    public void setAvailableRequest(ArrayList<Request> availableRequest) {
        this.availableRequests = availableRequest;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }


}
