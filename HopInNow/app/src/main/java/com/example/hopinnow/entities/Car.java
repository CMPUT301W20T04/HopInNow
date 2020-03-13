package com.example.hopinnow.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
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

    protected Car(Parcel in) {
        make = in.readString();
        model = in.readString();
        color = in.readString();
        plateNumber = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(make);
        parcel.writeString(model);
        parcel.writeString(color);
        parcel.writeString(plateNumber);
    }
}
