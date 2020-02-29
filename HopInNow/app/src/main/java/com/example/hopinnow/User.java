package com.example.hopinnow;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class User {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private int rating;
    /**??? map is not entity specific but a view/controller
     * place it under a fragment class instead
     * https://github.com/CMPUT301F19T02/Vybe/blob/master/app/src/main/java/com/example/vybe/MapFragment.java*/
    //private GoogleMap myMap;

    public User(){}

    public User(String email, String password, String name, String phoneNumber){
        this.email = email; //unique
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber; //unique?
    }

    //setters for email, password, name, phoneNumber, trip data
    public void setEmail(String email){ this.email = email;}

    public void setPassword(String password){ this.password = password;}

    public void setName(String name){ this.name = name;}

    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}

    public void setRating(int rating) { this.rating = rating; }

    //getters for email, password, name, phoneNumber, trip data
    public String getEmail(String email){ return this.email;}

    public String getPassword(String password){ return this.password;}

    public String getName(String name){ return this.name;}

    public String getPhoneNumber(String phoneNumber){ return this.phoneNumber;}

    public int getRating() { return rating; }


}
