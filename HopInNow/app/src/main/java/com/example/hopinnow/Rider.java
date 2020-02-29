package com.example.hopinnow;

import java.util.ArrayList;

public class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> riderTripList;

    //constructor
    public Rider(String email, String password, String name, String phoneNumber, Request curRequest, ArrayList<Trip> riderTripList) {
        super(email, password, name, phoneNumber);
        this.curRequest = curRequest;
        this.riderTripList = riderTripList;
    }

    //getter
    public Request getCurRequest() {
        return curRequest;
    }

    public ArrayList<Trip> getRiderTripList() {
        return riderTripList;
    }


    //setter
    public void setRiderTripList(ArrayList<Trip> riderTripList) {
        this.riderTripList = riderTripList;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }


}
