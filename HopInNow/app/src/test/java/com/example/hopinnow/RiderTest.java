package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.entities.Rider;
import com.example.hopinnow.entities.Trip;
import com.example.hopinnow.helperclasses.LatLong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RiderTest {
    // set up test entity
    private Rider mockRider() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String strdate = "02-04-2013 11:35:42";
        Date date = dateFormat.parse("06/03/1999");
        Car car = new Car("TTest", "tester", "Testor", "tst101");
        Request mockRequest = new Request("driverEmail@test.ca", "riderEmail@test.ca",
                new LatLong(30.25, 30.25), new LatLong(55.55, 55.55),
                "testPickupName", "testDropOffName", date,
                car, 5.67);
        Trip trip = new Trip();
        trip.setCost(13.23);
        return new Rider("test@ualberta.ca", "testpassword", "tester", "1234567",
        false, 123.25,
        mockRequest, ArrayList<Trip > riderTripList);
    }
}
