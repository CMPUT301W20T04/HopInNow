package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.LatLong;

import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * request entity test
 */
public class RequestTest {
    /**
     * set up test entity
     * @return
     *      return a request object
     */
    private Request mockRequest(){
        Car mockCar = new Car("Nissan", "Altima", "Black", "AAA-0001");
        LatLong pickUpLoc = new LatLong(10, 20);
        LatLong dropOffLoc = new LatLong(5, 10);
        Date pickUpTime = new Date();
        return new Request("driver", "rider", pickUpLoc, dropOffLoc,
                "pickUp",  "dropOff", pickUpTime, mockCar, 10.0);
    }

    /**
     * test on empty driver
     */
    @Test
    public void testEmpty(){
        Request mockRequest = new Request();
        assertThrows(NullPointerException.class, mockRequest::getEstimatedFare);
        assertThrows(NullPointerException.class, mockRequest::getDropOffLoc);
        assertThrows(NullPointerException.class, mockRequest::getDropOffLocName);
        assertThrows(NullPointerException.class, mockRequest::getPickUpDateTime);
        assertThrows(NullPointerException.class, mockRequest::getPickUpLoc);
        assertThrows(NullPointerException.class, mockRequest::getRiderEmail);
    }


    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck() {
        Request mockRequest = mockRequest();
        assertEquals(11, mockRequest.getPickUpLoc().getLat(), 1);
        assertEquals(21, mockRequest.getPickUpLoc().getLng(), 1);
        assertEquals(6, mockRequest.getDropOffLoc().getLat(), 1);
        assertEquals(11, mockRequest.getDropOffLoc().getLng(), 1);
        assertEquals("pickUp", mockRequest.getPickUpLocName());
        assertEquals("dropOff", mockRequest.getDropOffLocName());
        assertEquals("Nissan", mockRequest.getCar().getMake());
        assertEquals("Altima", mockRequest.getCar().getModel());
        assertEquals("Black", mockRequest.getCar().getColor());
        assertEquals("AAA-0001", mockRequest.getCar().getPlateNumber());
        assertEquals(11.0, mockRequest.getEstimatedFare(), 1);
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        Request mockRequest = mockRequest();
        Car car = new Car("newMake", "newModel", "newColor", "newPlate");
        LatLong newPickUpLoc = new LatLong(50, 110);
        LatLong newDropOffLoc = new LatLong(3, 13);
        Date newPickUpTime = new Date();
        mockRequest.setEstimatedFare(5.0);
        mockRequest.setCar(car);
        mockRequest.setDriverEmail("newDriver@gmail.com");
        mockRequest.setDropOffLoc(newDropOffLoc);
        mockRequest.setPickUpLoc(newPickUpLoc);
        mockRequest.setPickUpDateTime(newPickUpTime);
        mockRequest.setPickUpLocName("newPickUp");
        mockRequest.setDropOffLocName("newDropOff");
        mockRequest.setRequestID("001");
        assertEquals(51, mockRequest.getPickUpLoc().getLat(), 1);
        assertEquals(111, mockRequest.getPickUpLoc().getLng(), 1);
        assertEquals(4, mockRequest.getDropOffLoc().getLat(), 1);
        assertEquals(14, mockRequest.getDropOffLoc().getLng(), 1);
        assertEquals("newPickUp", mockRequest.getPickUpLocName());
        assertEquals("newDropOff", mockRequest.getDropOffLocName());
        assertEquals("newMake", mockRequest.getCar().getMake());
        assertEquals("newModel", mockRequest.getCar().getModel());
        assertEquals("newColor", mockRequest.getCar().getColor());
        assertEquals("newPlate", mockRequest.getCar().getPlateNumber());
        assertEquals(6.0, mockRequest.getEstimatedFare(), 1);
        assertEquals("001", mockRequest.getRequestID());
    }
}
