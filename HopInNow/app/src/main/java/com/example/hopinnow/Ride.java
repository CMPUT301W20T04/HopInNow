package com.example.hopinnow;

import java.sql.Time;

class Ride {
    private Driver driver;
    private Rider rider;
    private Location pickupLoc;
    private Location dropoffLoc;
    private Time pickupTime;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Location getPickupLoc() {
        return pickupLoc;
    }

    public void setPickupLoc(Location pickupLoc) {
        this.pickupLoc = pickupLoc;
    }

    public Location getDropoffLoc() {
        return dropoffLoc;
    }

    public void setDropoffLoc(Location dropoffLoc) {
        this.dropoffLoc = dropoffLoc;
    }

    public Time getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Time pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Time getDropoffTime() {
        return dropoffTime;
    }

    public void setDropoffTime(Time dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private Time dropoffTime;
    private double price;
}
