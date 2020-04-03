package com.example.hopinnow.entities;

import java.io.Serializable;

/**
 * Author: Shuwei Wang
 * Version: 1.0.0
 * user class which defines all the functionality and entities for rider and driver
 */
// TODO: eventually change to Parcelable
public class User implements Serializable {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Double deposit;
    private boolean userType;
    private Double rating = 0.0;
    private int ratingCounts = 0;

    /**
     * empty constructor
     */
    public User(){}

    /**
     * user constructor
     * @param email
     *      email
     * @param password
     *      password
     * @param name
     *      name
     * @param phoneNumber
     *      phone number
     * @param userType
     *      user type
     * @param deposit
     *      deposit
     */
    public User(String email, String password, String name, String phoneNumber, boolean userType,double deposit){
        this.email = email; //unique
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.deposit = deposit;
    }

    /**
     * get user email
     * can be null for rider to send request
     * @return
     *      email
     */
    public String getEmail() {
        return email;
    }

    /**
     * set user email
     * @param email
     *      email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get user password
     * can be null for security reason
     * @return
     *      password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set user password
     * @param password
     *      password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get user name
     * @return
     *      name
     */
    public String getName() {
        if (name == null){
            throw new NullPointerException();
        }
        else{
            return name;
        }
    }

    /**
     * set user name
     * @param name
     *      name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get phone number
     * @return
     *      phone number
     */
    public String getPhoneNumber() {
        if (phoneNumber == null){
            throw new NullPointerException();
        }
        else{
            return phoneNumber;
        }
    }

    /**
     * set phone number
     * @param phoneNumber
     *      phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get user deposit
     * @return
     *      deposit
     */
    public double getDeposit() {
        if (deposit == null){
            throw new NullPointerException();
        }
        else{
            return deposit;
        }
    }

    /**
     * set user deposit
     * @param deposit
     *      deposit
     */
    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    /**
     * check user type, true for driver and false for rider
     * @return
     *      type of the user
     */
    public boolean isUserType() {
        return userType;
    }

    /**
     * set user type, true for driver and false for rider
     * @param userType
     *      user type
     */
    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    /**
     * get the driver rating
     * @return
     *      get the rating of this driver:
     */
    public Double getRating() {
        return rating;
    }

    /**
     * set driver rating
     * @param rating
     *      set the rating of this driver:
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * get number of ratings
     * @return
     *      get the number of ratings of this driver:
     */
    public int getRatingCounts(){
        return ratingCounts;
    }

    /**
     * set the number of ratings
     * @param ratingCounts
     *      set the numbre of ratings of this driver:
     */
    public void setRatingCounts(int ratingCounts){
        this.ratingCounts = ratingCounts;
    }
}
