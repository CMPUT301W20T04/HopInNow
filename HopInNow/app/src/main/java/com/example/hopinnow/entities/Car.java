package com.example.hopinnow.entities;

import java.io.Serializable;

public class Car implements Serializable {
    private String make;
    private String model;
    private String color;
    private String plateNumber;

    //constructor
    public Car(){}
    public Car(String make, String model, String color, String plateNumber) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.plateNumber = plateNumber;
    }

    //getter
    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    //setter
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
