package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Driver;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.helperclasses.LatLong;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TripTest {
    // set up test entity
    private Trip mockTrip(){
        Car mockCar = new Car("Nissan", "Altima", "Black", "AAA-0001");
        LatLong pickUpLoc = new LatLong(10, 20);
        LatLong dropOffLoc = new LatLong(5, 10);
        Date pickUpTime = new Date();
        Date dropOffTime = new Date();
        Trip mockTrip = new Trip("driver", "rider", pickUpLoc, dropOffLoc,
                "pickUp",  "dropOff", pickUpTime, dropOffTime, 10, mockCar, 1.1, 2.1);
        return mockTrip;
    }
    // test on empty driver
    @Test
    public void testEmpty(){
        Trip trip = new Trip();
        assertThrows(NullPointerException.class, () -> {
            trip.getRating();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getCost();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getDropOffTime();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getDuration();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getCar();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getDriverEmail();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getDropOffLoc();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getDropOffLocName();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getPickUpDateTime();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getPickUpLoc();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getPickUpLocName();
        });
        assertThrows(NullPointerException.class, () -> {
            trip.getRiderEmail();
        });
    }

    // test on checking entity elements
    @Test
    public void testCheck() {
        Trip mockTrip = mockTrip();
        assertEquals(10, mockTrip.getPickUpLoc().getLat(), 0);
        assertEquals(20, mockTrip.getPickUpLoc().getLng(), 0);
        assertEquals(5, mockTrip.getDropOffLoc().getLat(), 0);
        assertEquals(10, mockTrip.getDropOffLoc().getLng(), 0);
        assertEquals("pickUp", mockTrip.getPickUpLocName());
        assertEquals("dropOff", mockTrip.getDropOffLocName());
        assertEquals(10, mockTrip.getDuration(), 0);
        assertEquals("Nissan", mockTrip.getCar().getMake());
        assertEquals("Altima", mockTrip.getCar().getModel());
        assertEquals("Black", mockTrip.getCar().getColor());
        assertEquals("AAA-0001", mockTrip.getCar().getPlateNumber());
        assertEquals(1.1, mockTrip.getCost(), 0);
        assertEquals(2.1, mockTrip.getRating(), 0);
    }
    // test on modifying entity elements
    @Test
    public void testEdit() {
        Trip mockTrip = mockTrip();
        Car car = new Car("newMake", "newModel", "newColor", "newPlate");
        LatLong newPickUpLoc = new LatLong(50, 110);
        LatLong newDropOffLoc = new LatLong(3, 13);
        Date newPickUpTime = new Date();
        Date newDropOffTime = new Date();
        mockTrip.setCost(2.0);
        mockTrip.setDropOffTime(newDropOffTime);
        mockTrip.setDuration(10);
        mockTrip.setCar(car);
        mockTrip.setRating(3.0);
        mockTrip.setDriverEmail("newDriver@gmail.com");
        mockTrip.setDropOffLoc(newDropOffLoc);
        mockTrip.setPickUpLoc(newPickUpLoc);
        mockTrip.setPickUpDateTime(newPickUpTime);
        mockTrip.setPickUpLocName("newPickUp");
        mockTrip.setDropOffLocName("newDropOff");
        assertEquals(50, mockTrip.getPickUpLoc().getLat(), 0);
        assertEquals(110, mockTrip.getPickUpLoc().getLng(), 0);
        assertEquals(3, mockTrip.getDropOffLoc().getLat(), 0);
        assertEquals(13, mockTrip.getDropOffLoc().getLng(), 0);
        assertEquals("newPickUp", mockTrip.getPickUpLocName());
        assertEquals("newDropOff", mockTrip.getDropOffLocName());
        assertEquals(10, mockTrip.getDuration(), 0);
        assertEquals("newMake", mockTrip.getCar().getMake());
        assertEquals("newModel", mockTrip.getCar().getModel());
        assertEquals("newColor", mockTrip.getCar().getColor());
        assertEquals("newPlate", mockTrip.getCar().getPlateNumber());
        assertEquals(2.0, mockTrip.getCost(), 0);
        assertEquals(3.0, mockTrip.getRating(), 0);
    }
}
