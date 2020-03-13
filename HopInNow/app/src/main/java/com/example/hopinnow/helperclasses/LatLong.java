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

    /**
     * return the latitude
     * @return lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * set the longitude
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * get the longitude
     * @return lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * set the longitude
     * @param lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }
}
