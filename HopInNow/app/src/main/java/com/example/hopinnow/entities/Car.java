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
        try {
            this.make = make;
            this.model = model;
            this.color = color;
            this.plateNumber = plateNumber;
        }
        catch (Exception e){
            throw e;
        }
    }

    //getter
    public String getColor() {
        if (color == null){
            throw new NullPointerException();
        }
        else{
            return color;
        }
    }

    public String getMake() {
        if (make == null){
            throw new NullPointerException();
        }
        else{
            return make;
        }
    }

    public String getModel() {
        if (model == null){
            throw new NullPointerException();
        }
        else{
            return model;
        }
    }

    public String getPlateNumber() {
        if (plateNumber == null){
            throw new NullPointerException();
        }
        else{
            return plateNumber;
        }
    }

    //setter
    public void setPlateNumber(String plateNumber) {
        try{
            this.plateNumber = plateNumber;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setModel(String model) {
        try{
            this.model = model;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setMake(String make) {
        try{
            this.make = make;
        }
        catch (Exception e){
            throw e;
        }
    }

    public void setColor(String color) {
        try{
            this.color = color;
        }
        catch (Exception e){
            throw e;
        }
    }
}
