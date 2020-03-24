package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.entities.LatLong;

import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * trip entity test
 */
public class TripTest {
    /**
     * set up test entity
     * @return
     */
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
    /**
     * test on empty driver
     */
    @Test
    public void testEmpty() {
        Trip trip = new Trip();
        assertThrows(NullPointerException.class, trip::getRating);
        assertThrows(NullPointerException.class, trip::getCost);
        assertThrows(NullPointerException.class, trip::getDropOffTime);
        assertThrows(NullPointerException.class, trip::getDuration);
        assertThrows(NullPointerException.class, trip::getDropOffLoc);
        assertThrows(NullPointerException.class, trip::getDropOffLocName);
        assertThrows(NullPointerException.class, trip::getPickUpDateTime);
        assertThrows(NullPointerException.class, trip::getPickUpLoc);
        assertThrows(NullPointerException.class, trip::getPickUpLocName);
        assertThrows(NullPointerException.class, trip::getRiderEmail);
    }

    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck() {
        Trip mockTrip = mockTrip();
        assertEquals(11, mockTrip.getPickUpLoc().getLat(), 1);
        assertEquals(21, mockTrip.getPickUpLoc().getLng(), 1);
        assertEquals(6, mockTrip.getDropOffLoc().getLat(), 1);
        assertEquals(11, mockTrip.getDropOffLoc().getLng(), 1);
        assertEquals("pickUp", mockTrip.getPickUpLocName());
        assertEquals("dropOff", mockTrip.getDropOffLocName());
        assertEquals(11, mockTrip.getDuration(), 1);
        assertEquals("Nissan", mockTrip.getCar().getMake());
        assertEquals("Altima", mockTrip.getCar().getModel());
        assertEquals("Black", mockTrip.getCar().getColor());
        assertEquals("AAA-0001", mockTrip.getCar().getPlateNumber());
        assertEquals(2.1, mockTrip.getCost(), 1);
        assertEquals(3.1, mockTrip.getRating(), 1);
    }

    /**
     * test on modifying entity elements
     */
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
        assertEquals(51, mockTrip.getPickUpLoc().getLat(), 1);
        assertEquals(111, mockTrip.getPickUpLoc().getLng(), 1);
        assertEquals(4, mockTrip.getDropOffLoc().getLat(), 1);
        assertEquals(14, mockTrip.getDropOffLoc().getLng(), 1);
        assertEquals("newPickUp", mockTrip.getPickUpLocName());
        assertEquals("newDropOff", mockTrip.getDropOffLocName());
        assertEquals(11, mockTrip.getDuration(), 1);
        assertEquals("newMake", mockTrip.getCar().getMake());
        assertEquals("newModel", mockTrip.getCar().getModel());
        assertEquals("newColor", mockTrip.getCar().getColor());
        assertEquals("newPlate", mockTrip.getCar().getPlateNumber());
        assertEquals(3.0, mockTrip.getCost(), 1);
        assertEquals(4.0, mockTrip.getRating(), 1);
    }
}
