package com.example.hopinnow.entities;

import java.io.Serializable;

/**
 * Author: Shuway Wang
 * Version: 1.0.0
 * The class that saves car information
 */
public class Car implements Serializable {
    private String make;
    private String model;
    private String color;
    private String plateNumber;

    /**
     * Empty Constructor
     */
    public Car(){}

    /**
     * Constructor
     * @param make
     * @param model
     * @param color
     * @param plateNumber
     */
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


    /**
     * get car color
     * @return
     */
    public String getColor() {
        if (color == null){
            throw new NullPointerException();
        }
        else{
            return color;
        }
    }

    /**
     * get car make
     * @return
     */
    public String getMake() {
        if (make == null){
            throw new NullPointerException();
        }
        else{
            return make;
        }
    }

    /**
     * get car model
     * @return
     */
    public String getModel() {
        if (model == null){
            throw new NullPointerException();
        }
        else{
            return model;
        }
    }

    /**
     * get plate number
     * @return
     */
    public String getPlateNumber() {
        if (plateNumber == null){
            throw new NullPointerException();
        }
        else{
            return plateNumber;
        }
    }

    /**
     * set plate number
     * @param plateNumber
     */
    public void setPlateNumber(String plateNumber) {
        try{
            this.plateNumber = plateNumber;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set car model
     * @param model
     */
    public void setModel(String model) {
        try{
            this.model = model;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set car make
     * @param make
     */
    public void setMake(String make) {
        try{
            this.make = make;
        }
        catch (Exception e){
            throw e;
        }
    }

    /**
     * set car color
     * @param color
     */
    public void setColor(String color) {
        try{
            this.color = color;
        }
        catch (Exception e){
            throw e;
        }
    }
}
