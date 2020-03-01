package com.example.hopinnow.entities;

import java.util.ArrayList;

public class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> riderTripList;

    //constructor
    public Rider() {}
    public Rider(String email, String password, String name, String phoneNumber, Request curRequest,
                 ArrayList<Trip> riderTripList) {
        super(email, password, name, phoneNumber);
        this.curRequest = curRequest;
        this.riderTripList = riderTripList;
    }

    // setters and getters
    public Request getCurRequest() {
        return curRequest;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }

    public ArrayList<Trip> getRiderTripList() {
        return riderTripList;
    }

    public void setRiderTripList(ArrayList<Trip> riderTripList) {
        this.riderTripList = riderTripList;
    }
}
