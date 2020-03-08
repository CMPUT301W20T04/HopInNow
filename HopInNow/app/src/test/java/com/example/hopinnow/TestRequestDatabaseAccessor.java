package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.User;

/**
 * Author: Shway Wang
 * Tests for RequestDatabaseAccessor class:
 */
public class TestRequestDatabaseAccessor {
    private User mockupUser() {
        User user = new User();
        user.setEmail("tester@test.ca");
        user.setName("Tester");
        user.setPassword("1234567");
        user.setPhoneNumber("14159265");
        return user;
    }
    private Rider mockupRider(User user) {
        Rider rider = (Rider)user;
        rider.setUserType(false);
        return rider;
    }
    private Driver mockupDriver(User user) {
        Driver driver = (Driver)user;
        driver.setUserType(true);
        Car car = new Car();
        car.setMake("Test Make");
        car.setModel("Test Model");
        car.setColor("Test Color");
        car.setPlateNumber("Test Plate Number");
        driver.setCar(car);
        return driver;
    }
}
