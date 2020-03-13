package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.helperclasses.LatLong;
import org.junit.Test;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * rider entity test
 */
public class RiderTest {
    /**
     * set up test entity
     * @return
     * @throws ParseException
     */
    private Rider mockRider() throws ParseException {
        Car mockCar = new Car("Nissan", "Altima", "Black",
                "AAA-0001");
        LatLong pickUpLoc = new LatLong(10, 20);
        LatLong dropOffLoc = new LatLong(5, 10);
        Date pickUpTime = new Date();
        Date dropOffTime = new Date();
        Request mockRequest = new Request("rider", "rider", pickUpLoc, dropOffLoc,
                "pickUp",
                "dropOff", pickUpTime, mockCar, 0.0);
        ArrayList<Trip> riderTripList = new ArrayList<Trip>();
        Trip mockTrip = new Trip("rider", "rider", pickUpLoc, dropOffLoc,
                "pickUp",  "dropOff", pickUpTime, dropOffTime,
                10, mockCar, 1.1, 2.1);
        ArrayList<Trip> mockTripList = new ArrayList<Trip>();
        mockTripList.add(mockTrip);
        return new Rider("rider@gmail.com", "abc123456",
                "rider", "7801230000", false,
                0.0, mockRequest, mockTripList);
    }
    /**
     * test on empty driver
     */
    @Test
    public void testEmpty(){
        Rider rider = new Rider();
        assertThrows(NullPointerException.class, () -> {
            rider.getDeposit();
        });
        assertThrows(NullPointerException.class, () -> {
            rider.getDeposit();
        });
        assertThrows(NullPointerException.class, () -> {
            rider.getName();
        });
        assertThrows(NullPointerException.class, () -> {
            rider.getPhoneNumber();
        });
    }

    /**
     * test on checking entity elements
     * @throws ParseException
     */
    @Test
    public void testCheck() throws ParseException {
        Rider rider = mockRider();
        assertEquals("rider@gmail.com", rider.getEmail());
        assertEquals("abc123456", rider.getPassword());
        assertEquals("rider", rider.getName());
        assertEquals("7801230000", rider.getPhoneNumber());
        assertFalse(rider.isUserType());
        assertEquals(1.0, rider.getDeposit(), 1);
        assertEquals(11.0, rider.getCurRequest().getPickUpLoc().getLat(), 1);
        assertEquals(21.0, rider.getCurRequest().getPickUpLoc().getLng(), 1);
        assertEquals("pickUp", rider.getCurRequest().getPickUpLocName());
        assertEquals("dropOff", rider.getCurRequest().getDropOffLocName());
        assertEquals("Nissan", rider.getCurRequest().getCar().getMake());
        assertEquals("Altima", rider.getCurRequest().getCar().getModel());
        assertEquals("Black", rider.getCurRequest().getCar().getColor());
        assertEquals("AAA-0001", rider.getCurRequest().getCar().getPlateNumber());
        assertEquals(1.0, (double)rider.getCurRequest().getEstimatedFare(), 1);
        assertEquals(11, rider.getRiderTripList().get(0).getPickUpLoc().getLat(), 1);
        assertEquals(21, rider.getRiderTripList().get(0).getPickUpLoc().getLng(), 1);
        assertEquals(6, rider.getRiderTripList().get(0).getDropOffLoc().getLat(), 1);
        assertEquals(11, rider.getRiderTripList().get(0).getDropOffLoc().getLng(), 1);
        assertEquals("pickUp", rider.getRiderTripList().get(0).getPickUpLocName());
        assertEquals("dropOff", rider.getRiderTripList().get(0).getDropOffLocName());
        assertEquals(10, rider.getRiderTripList().get(0).getDuration());
        assertEquals("Nissan", rider.getRiderTripList().get(0).getCar().getMake());
        assertEquals("Altima", rider.getRiderTripList().get(0).getCar().getModel());
        assertEquals("Black", rider.getRiderTripList().get(0).getCar().getColor());
        assertEquals("AAA-0001", rider.getRiderTripList().get(0).getCar().getPlateNumber());
        assertEquals(2.1, (double)rider.getRiderTripList().get(0).getCost(), 1);
        assertEquals(3.1, (double)rider.getRiderTripList().get(0).getRating(), 1);
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        Car car = new Car("newMake", "newModel", "newColor", "newPlate");
        Request newRiderRequest = new Request();
        ArrayList<Trip> newRiderTripList = new ArrayList<Trip>();
        Rider newRider = new Rider("newRider@gmail.com", "newRiderPasswd", "newRider",
                "7801230001", false,
                1.0, newRiderRequest, newRiderTripList);
        Rider newRequestRider = new Rider();
        LatLong newPickUpLoc = new LatLong(50, -110);
        LatLong newDropOffLoc = new LatLong(53, -113);
        Date newPickUpTime = new Date();
        Date newDropOffTime = new Date();
        Request mockRequest = new Request("newRider", "newRider", newPickUpLoc, newDropOffLoc, "pickUp",
                "dropOff", newPickUpTime, car, 0.0);
        ArrayList<Request> newRequestList = new ArrayList<Request>();
        Trip newTrip = new Trip("newRider", "newRider", newPickUpLoc, newDropOffLoc,
                "pickUp",  "dropOff", newPickUpTime, newDropOffTime, 10,
                car, 1.1, 2.1);
        ArrayList<Trip> newTripList = new ArrayList<Trip>();
        newTripList.add(newTrip);
        Rider mockRider = new Rider("rider@gmail.com", "riderPasswd", "drier", "7800101234", true, 0.0,
                mockRequest, newTripList);
    }
}
