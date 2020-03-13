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

/**
 * driver entity test
 * Author: Hongru Qi
 * Version: 1.0.0
 */
public class DriverTest {
    /**
     * set up test entity
     * @return
     */
    private Driver mockDriver(){
        Car mockCar = new Car("Nissan", "Altima", "Black",
                "AAA-0001");
        Request riderRequest = new Request();
        ArrayList<Trip> driverTripList = new ArrayList<Trip>();
        Rider mockRider = new Rider("rider@gmail.com", "riderPasswd",
                "rider", "7801230000", false,
                0.0, riderRequest, driverTripList);
        Driver requestDriver = new Driver();
        LatLong pickUpLoc = new LatLong(10, 20);
        LatLong dropOffLoc = new LatLong(5, 10);
        Date pickUpTime = new Date();
        Date dropOffTime = new Date();
        Request mockRequest = new Request("driver", "rider", pickUpLoc, dropOffLoc,
                "pickUp",
                "dropOff", pickUpTime, mockCar, 0.0);
        ArrayList<Request> mockRequestList = new ArrayList<Request>();
        Trip mockTrip = new Trip("driver", "rider", pickUpLoc, dropOffLoc,
                "pickUp",  "dropOff", pickUpTime, dropOffTime,
                10, mockCar, 1.1, 2.1);
        ArrayList<Trip> mockTripList = new ArrayList<Trip>();
        mockTripList.add(mockTrip);
        Driver mockDriver = new Driver("driver@gmail.com", "abc123456",
                "driver", "7801230000", true, 0.0,
                mockRequest, mockCar, mockTripList);
        return mockDriver;
    }

    /**
     * test on empty driver
     */
    @Test
    public void testEmpty(){
        Driver driver = new Driver();
        assertThrows(NullPointerException.class, () -> {
            driver.getDriverTripList();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getCurRequest();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getCar();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getRating();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getRatingCounts();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getDeposit();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getEmail();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getName();
        });
        assertThrows(NullPointerException.class, () -> {
            driver.getPhoneNumber();
        });
    }

    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck() {
        Driver driver = mockDriver();
        assertEquals("driver@gmail.com", driver.getEmail());
        assertEquals("abc123456", driver.getPassword());
        assertEquals("driver", driver.getName());
        assertEquals("7801230000", driver.getPhoneNumber());
        assertEquals(true, driver.isUserType());
        assertEquals(0.0, driver.getDeposit(), 0);
        assertEquals(0.0, (double)driver.getRating(), 0);
        assertEquals(0, driver.getRatingCounts());
        assertEquals("Nissan", driver.getCar().getMake());
        assertEquals("Altima", driver.getCar().getModel());
        assertEquals("Black", driver.getCar().getColor());
        assertEquals("AAA-0001", driver.getCar().getPlateNumber());
        assertEquals(10, driver.getCurRequest().getPickUpLoc().getLat(), 0);
        assertEquals(20, driver.getCurRequest().getPickUpLoc().getLng(), 0);
        assertEquals("pickUp", driver.getCurRequest().getPickUpLocName());
        assertEquals("dropOff", driver.getCurRequest().getDropOffLocName());
        assertEquals("Nissan", driver.getCurRequest().getCar().getMake());
        assertEquals("Altima", driver.getCurRequest().getCar().getModel());
        assertEquals("Black", driver.getCurRequest().getCar().getColor());
        assertEquals("AAA-0001", driver.getCurRequest().getCar().getPlateNumber());
        assertEquals(0.0, (double)driver.getCurRequest().getEstimatedFare(), 0);
        assertEquals(10, driver.getDriverTripList().get(0).getPickUpLoc().getLat(), 0);
        assertEquals(20, driver.getDriverTripList().get(0).getPickUpLoc().getLng(), 0);
        assertEquals(5, driver.getDriverTripList().get(0).getDropOffLoc().getLat(), 0);
        assertEquals(10, driver.getDriverTripList().get(0).getDropOffLoc().getLng(), 0);
        assertEquals("pickUp", driver.getDriverTripList().get(0).getPickUpLocName());
        assertEquals("dropOff", driver.getDriverTripList().get(0).getDropOffLocName());
        assertEquals(10, driver.getDriverTripList().get(0).getDuration(), 0);
        assertEquals("Nissan", driver.getDriverTripList().get(0).getCar().getMake());
        assertEquals("Altima", driver.getDriverTripList().get(0).getCar().getModel());
        assertEquals("Black", driver.getDriverTripList().get(0).getCar().getColor());
        assertEquals("AAA-0001", driver.getDriverTripList().get(0).getCar().getPlateNumber());
        assertEquals(1.1, (double)driver.getDriverTripList().get(0).getCost(), 0);
        assertEquals(2.1, (double)driver.getDriverTripList().get(0).getRating(), 0);
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        Driver mockDriver = mockDriver();
        Car car = new Car("newMake", "newModel", "newColor", "newPlate");
        Request newRiderRequest = new Request();
        ArrayList<Trip> newDriverTripList = new ArrayList<Trip>();
        Rider newRider = new Rider("newRider@gmail.com", "newRiderPasswd", "newRider",
                "7801230001", false,
                1.0, newRiderRequest, newDriverTripList);
        Driver newRequestDriver = new Driver();
        LatLong newPickUpLoc = new LatLong(50, 110);
        LatLong newDropOffLoc = new LatLong(3, 13);
        Date newPickUpTime = new Date();
        Date newDropOffTime = new Date();
        Request mockRequest = new Request("newDriver", "newRider", newPickUpLoc, newDropOffLoc, "newPickUp",
                "newDropOff", newPickUpTime, car, 0.0);
        ArrayList<Request> newRequestList = new ArrayList<Request>();
        Trip newTrip = new Trip("newDriver", "newRider", newPickUpLoc, newDropOffLoc,
                "newPickUp",  "newDropOff", newPickUpTime, newDropOffTime, 10,
                car, 2.0, 3.0);
        ArrayList<Trip> newTripList = new ArrayList<Trip>();
        newTripList.add(newTrip);
        mockDriver.setCar(car);
        mockDriver.setCurRequest(mockRequest);
        mockDriver.setDriverTripList(newTripList);
        mockDriver.setEmail("newDriver@gmail.com");
        mockDriver.setPassword("newDriverPasswd");
        mockDriver.setName("newDriver");
        mockDriver.setPhoneNumber("7800101234");
        mockDriver.setDeposit(10);
        mockDriver.setRating(5.0);
        mockDriver.setRatingCounts(1);
        assertEquals("newDriver@gmail.com", mockDriver.getEmail());
        assertEquals("newDriverPasswd", mockDriver.getPassword());
        assertEquals("newDriver", mockDriver.getName());
        assertEquals("7800101234", mockDriver.getPhoneNumber());
        assertEquals(true, mockDriver.isUserType());
        assertEquals(10, mockDriver.getDeposit(), 0);
        assertEquals(5.0, (double)mockDriver.getRating(), 0);
        assertEquals(1, mockDriver.getRatingCounts());
        assertEquals("newMake", mockDriver.getCar().getMake());
        assertEquals("newModel", mockDriver.getCar().getModel());
        assertEquals("newColor", mockDriver.getCar().getColor());
        assertEquals("newPlate", mockDriver.getCar().getPlateNumber());
        assertEquals(50, mockDriver.getCurRequest().getPickUpLoc().getLat(), 0);
        assertEquals(110, mockDriver.getCurRequest().getPickUpLoc().getLng(), 0);
        assertEquals("newPickUp", mockDriver.getCurRequest().getPickUpLocName());
        assertEquals("newDropOff", mockDriver.getCurRequest().getDropOffLocName());
        assertEquals("newMake", mockDriver.getCurRequest().getCar().getMake());
        assertEquals("newModel", mockDriver.getCurRequest().getCar().getModel());
        assertEquals("newColor", mockDriver.getCurRequest().getCar().getColor());
        assertEquals("newPlate", mockDriver.getCurRequest().getCar().getPlateNumber());
        assertEquals(0.0, (double)mockDriver.getCurRequest().getEstimatedFare(), 0);
        assertEquals(50, mockDriver.getDriverTripList().get(0).getPickUpLoc().getLat(), 0);
        assertEquals(110, mockDriver.getDriverTripList().get(0).getPickUpLoc().getLng(), 0);
        assertEquals(3, mockDriver.getDriverTripList().get(0).getDropOffLoc().getLat(), 0);
        assertEquals(13, mockDriver.getDriverTripList().get(0).getDropOffLoc().getLng(), 0);
        assertEquals("newPickUp", mockDriver.getDriverTripList().get(0).getPickUpLocName());
        assertEquals("newDropOff", mockDriver.getDriverTripList().get(0).getDropOffLocName());
        assertEquals(10, mockDriver.getDriverTripList().get(0).getDuration(), 0);
        assertEquals("newMake", mockDriver.getDriverTripList().get(0).getCar().getMake());
        assertEquals("newModel", mockDriver.getDriverTripList().get(0).getCar().getModel());
        assertEquals("newColor", mockDriver.getDriverTripList().get(0).getCar().getColor());
        assertEquals("newPlate", mockDriver.getDriverTripList().get(0).getCar().getPlateNumber());
        assertEquals(2.0, (double)mockDriver.getDriverTripList().get(0).getCost(), 0);
        assertEquals(3.0, (double)mockDriver.getDriverTripList().get(0).getRating(), 0);
    }
}
