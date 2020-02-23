package com.example.hopinnow;

import java.util.ArrayList;

class Rider extends User {
    private Request curRequest;
    private ArrayList<Trip> tripList;

    public Request getCurRequest() {
        return curRequest;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }

    @Override
    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    @Override
    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }
}
