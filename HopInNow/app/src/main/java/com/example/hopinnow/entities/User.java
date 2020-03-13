package com.example.hopinnow.entities;

import java.io.Serializable;

/**
 * user class which defines all the functionality and entities for rider and driver
 * Author: Shuwei Wang
 * Version: 1.0.0
 */
// TODO: eventually change to Parcelable
public class User implements Serializable {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Double deposit;
    private boolean userType;

    /**
     * empty constructor
     */
    public User(){}

    /**
     * user constructor
     * @param email
     * @param password
     * @param name
     * @param phoneNumber
     * @param userType
     * @param deposit
     */
    public User(String email, String password, String name, String phoneNumber, boolean userType,double deposit){
        try{
            this.email = email; //unique
            this.password = password;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.userType = userType;
            this.deposit = deposit;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get user email
     * can be null for rider to send request
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * set user email
     * @param email
     */
    public void setEmail(String email) {
        try{
            this.email = email;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get user password
     * can be null for security reason
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * set user password
     * @param password
     */
    public void setPassword(String password) {
        try{
            this.password = password;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get user name
     * @return
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
     */
    public void setName(String name) {
        try{
            this.name = name;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get phone number
     * @return
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
     */
    public void setPhoneNumber(String phoneNumber) {
        try{
            this.phoneNumber = phoneNumber;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * get user deposit
     * @return
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
     */
    public void setDeposit(double deposit) {
        try{
            this.deposit = deposit;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * check user type, true for driver and false for rider
     * @return
     */
    public boolean isUserType() {
        return userType;
    }

    /**
     * set user type, true for driver and false for rider
     * @param userType
     */
    public void setUserType(boolean userType) {
        try{
            this.userType = userType;
        }
        catch (Exception e){
            throw e;
        }
    }
}
