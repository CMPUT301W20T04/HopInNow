package com.example.hopinnow;

import java.util.ArrayList;

public class Driver extends User {
    private Request curRide;
    private Car car;
    private int rating;
    private ArrayList<Request> availableRequests;
    Driver() {}
    public Driver(String name, String password, String phoneNumber, String email) {
        super(name, password, phoneNumber, email);
    }

    public Request getCurRide() {
        return curRide;
    }

    public void setCurRide(Request curRide) {
        this.curRide = curRide;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<Request> getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(ArrayList<Request> availableRequests) {
        this.availableRequests = availableRequests;
    }
}
