package com.example.hopinnow.entities;

import java.io.Serializable;

// TODO: eventually change to Parcelable
public class User implements Serializable {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private double deposit;
    private boolean userType;
    // constructors

    public User(){}
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
    // getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        try{
            this.email = email;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try{
            this.password = password;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try{
            this.name = name;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        try{
            this.phoneNumber = phoneNumber;
        }
        catch (Exception e){
            throw e;
        }
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        try{
            this.deposit = deposit;
        }
        catch (Exception e){
            throw e;
        }
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        try{
            this.userType = userType;
        }
        catch (Exception e){
            throw e;
        }
    }
}
