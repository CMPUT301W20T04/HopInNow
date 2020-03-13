package com.example.hopinnow;

import com.example.hopinnow.entities.Car;
import com.example.hopinnow.entities.EmergencyCall;
import com.example.hopinnow.entities.Request;
import com.example.hopinnow.helperclasses.LatLong;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class EmergencyCallTest {
    // set up test entity
    private EmergencyCall mockEmCall(){
        return new EmergencyCall("110");
    }
    // test on empty driver
    @Test
    public void testEmpty(){
        EmergencyCall call = new EmergencyCall();
        assertEquals("911", call.getDialogNumber());
    }

    // test on checking entity elements
    @Test
    public void testCheck() {
        EmergencyCall emCall = mockEmCall();
        assertEquals("110", emCall.getDialogNumber());
    }
    // test on modifying entity elements
    @Test
    public void testEdit() {
        EmergencyCall emCall = mockEmCall();
        emCall.setDialogNumber("119");
        assertEquals("119", emCall.getDialogNumber());
    }
}
