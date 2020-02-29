package com.example.hopinnow.Entities;

public abstract class User {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private double deposit;

    // constructors
    public User(){}

    public User(String email, String password, String name, String phoneNumber){
        this.email = email; //unique
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
