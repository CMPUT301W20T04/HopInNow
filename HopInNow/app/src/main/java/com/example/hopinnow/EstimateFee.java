package com.example.hopinnow;

import java.sql.Time;

public class EstimateFee {
    private Time time;
    private double distance;
    private double rate;
    private double estimateMoney;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getEstimateMoney() {
        return estimateMoney;
    }

    public void setEstimateMoney(double estimateMoney) {
        this.estimateMoney = estimateMoney;
    }
}
