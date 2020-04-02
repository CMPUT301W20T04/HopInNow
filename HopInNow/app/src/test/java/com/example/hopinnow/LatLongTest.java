package com.example.hopinnow;

import com.example.hopinnow.entities.EmergencyCall;
import com.example.hopinnow.entities.LatLong;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LatLongTest {
    /**
     * set up test entity
     * @return
     *      return an LatLong object
     */
    private LatLong mockLatLong(){
        return new LatLong(10.0, 20.0);
    }

    /**
     * test on empty entity
     */
    @Test
    public void testEmpty(){
        LatLong latLong = new LatLong();
        assertEquals(0.0, latLong.getLat());
        assertEquals(0.0, latLong.getLng());
    }

    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck() {
        LatLong latLong = mockLatLong();
        assertEquals(10.0, latLong.getLat());
        assertEquals(20.0, latLong.getLng());
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        LatLong latLong = mockLatLong();
        latLong.setLat(5.1);
        latLong.setLng(6.1);
        assertEquals(5.1, latLong.getLat());
        assertEquals(6.1, latLong.getLng());    }
}
