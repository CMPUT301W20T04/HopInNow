package com.example.hopinnow.helperclasses;

/**
 * Author: Shway Wang
 * Substitute for Google's LatLng class to be able to store into the firebase.
 * Needs to be cast to Google's LatLng manually.
 */
public class LatLong {
    private double lat;
    private double lng;
    public LatLong(){}
    public LatLong(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
