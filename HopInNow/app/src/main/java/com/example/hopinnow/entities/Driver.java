package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Driver extends User {
    private Request curRequest;
    private Car car;
    private ArrayList<Trip> driverTripList;
    private Double rating;
    private int ratingCounts;

    //constructor
    public Driver(){}
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
    // setters and getters
    public Request getCurRequest() {
        if (curRequest == null){
            throw new NullPointerException();
        }
        else{
            return curRequest;
        }
    }

    public void setCurRequest(Request curRequest) {
        try{
            this.curRequest = curRequest;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Car getCar() {
        if (car == null){
            throw new NullPointerException();
        }
        else{
            return car;
        }
    }

    public void setCar(Car car) {
        try{
            this.car = car;
        }
        catch(Exception e){
            throw e;
        }
    }

    public ArrayList<Trip> getDriverTripList() {
        if (driverTripList == null){
            throw new NullPointerException();
        }
        else{
            return driverTripList;
        }
    }

    public void setDriverTripList(ArrayList<Trip> driverTripList) {
        try{
            this.driverTripList = driverTripList;
        }
        catch (Exception e){
            throw e;
        }
    }

    public Double getRating() {
        if (rating == null){
            throw new NullPointerException();
        }
        else{
            return rating;
        }
    }

    public void setRating(Double rating) {
        try{
            this.rating = rating;
        }
        catch (Exception e){
            throw e;
        }
    }

    public int getRatingCounts(){ return ratingCounts;}

    public void setRatingCounts(int ratingCounts){
        try{
            this.ratingCounts = ratingCounts;
        }
        catch(Exception e){
            throw e;
        }
    }

}
