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
import static org.junit.Assert.assertTrue;

public class DriverTest {
    // set up test entity
    private Driver mockDriver(){
        Car mockCar = new Car("Nissan", "Altima", "Black",
                "AAA-0001");
        Request riderRequest = new Request();
        ArrayList<Trip> driverTripList = new ArrayList<Trip>();
        Rider mockRider = new Rider("rider@gmail.com", "riderPasswd",
                "rider", "7801230000", false,
                0.0, riderRequest, driverTripList);
        Driver requestDriver = new Driver();
        LatLong pickUpLoc = new LatLong(53.631611, -113.323975);
        LatLong dropOffLoc = new LatLong(53.631611, -113.323975);
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
                "drier", "7801230000", true, 0.0,
                mockRequest, mockCar, mockTripList);
        return mockDriver;
    }
    // test on checking entity elements
    @Test
    public void testCheck() {
        Driver driver = mockDriver();
        assertEquals("driver@gmail.com", driver.getEmail());
        assertEquals("abc123456", driver.getPassword());
        assertEquals("drviver", driver.getName());
        assertEquals("7801230000", driver.getPhoneNumber());
        assertEquals(true, driver.isUserType());
        assertEquals(0.0, driver.getDeposit());
        assertEquals(0.0, (double)driver.getRating());
        assertEquals(0, driver.getRatingCounts());
        assertEquals("Nissan", driver.getCar().getMake());
        assertEquals("Altima", driver.getCar().getModel());
        assertEquals("Black", driver.getCar().getColor());
        assertEquals("AAA-0001", driver.getCar().getPlateNumber());
        assertEquals("53.631611", driver.getCurRequest().getPickUpLoc().getLat());
        assertEquals("-113.323975", driver.getCurRequest().getPickUpLoc().getLng());
        assertEquals("pickUp", driver.getCurRequest().getPickUpLocName());
        assertEquals("dropOff", driver.getCurRequest().getDropOffLocName());
        assertEquals("Nissan", driver.getCurRequest().getCar().getMake());
        assertEquals("Altima", driver.getCurRequest().getCar().getModel());
        assertEquals("Black", driver.getCurRequest().getCar().getColor());
        assertEquals("AAA-0001", driver.getCurRequest().getCar().getPlateNumber());
        assertEquals(0.0, (double)driver.getCurRequest().getEstimatedFare());
        assertEquals("53.631611", driver.getDriverTripList().get(0).getPickUpLoc());
        assertEquals("-113.323975", driver.getDriverTripList().get(0).getDropOffLoc());
        assertEquals("pickUp", driver.getDriverTripList().get(0).getPickUpLocName());
        assertEquals("dropOff", driver.getDriverTripList().get(0).getDropOffLocName());
        assertEquals(10, driver.getDriverTripList().get(0).getDuration());
        assertEquals("Nissan", driver.getDriverTripList().get(0).getCar().getMake());
        assertEquals("Altima", driver.getDriverTripList().get(0).getCar().getModel());
        assertEquals("Black", driver.getDriverTripList().get(0).getCar().getColor());
        assertEquals("AAA-0001", driver.getDriverTripList().get(0).getCar().getPlateNumber());
        assertEquals(1.1, (double)driver.getDriverTripList().get(0).getCost());
        assertEquals(2.1, (double)driver.getDriverTripList().get(0).getRating());
    }
    // test on modifying entity elements
    @Test
    public void testEdit() {
        Car car = new Car("newMake", "newModel", "newColor", "newPlate");
        Request newRiderRequest = new Request();
        ArrayList<Trip> newDriverTripList = new ArrayList<Trip>();
        Rider newRider = new Rider("newRider@gmail.com", "newRiderPasswd", "newRider",
                "7801230001", false,
                1.0, newRiderRequest, newDriverTripList);
        Driver newRequestDriver = new Driver();
        LatLong newPickUpLoc = new LatLong(50, -110);
        LatLong newDropOffLoc = new LatLong(53, -113);
        Date newPickUpTime = new Date();
        Date newDropOffTime = new Date();
        Request mockRequest = new Request("newDriver", "newRider", newPickUpLoc, newDropOffLoc, "pickUp",
                "dropOff", newPickUpTime, car, 0.0);
        ArrayList<Request> newRequestList = new ArrayList<Request>();
        Trip newTrip = new Trip("newDriver", "newRider", newPickUpLoc, newDropOffLoc,
                "pickUp",  "dropOff", newPickUpTime, newDropOffTime, 10,
                car, 1.1, 2.1);
        ArrayList<Trip> newTripList = new ArrayList<Trip>();
        newTripList.add(newTrip);
        Driver mockDriver = new Driver("driver@gmail.com", "driverPasswd", "drier", "7800101234", true, 0.0,
                mockRequest, car, newTripList);
    }
}
