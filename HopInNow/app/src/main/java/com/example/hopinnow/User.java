package com.example.hopinnow;

import java.util.ArrayList;

public abstract class User {
    private double userDeposit;
    private String name;
    private String password;
    private String phoneNumber;
    private String email;
    private ArrayList<Trip> tripList;

    User() {}

    User(String name, String password, String phoneNumber, String email) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public double getUserDeposit() {
        return userDeposit;
    }

    public void setUserDeposit(double userDeposit) {
        this.userDeposit = userDeposit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }
}
