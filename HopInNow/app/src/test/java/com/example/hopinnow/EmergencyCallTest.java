package com.example.hopinnow;

import com.example.hopinnow.entities.EmergencyCall;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * emergency call entity test
 */
public class EmergencyCallTest {
    /**
     * set up test entity
     * @return
     *      return an EmergencyCall object
     */
    private EmergencyCall mockEmCall(){
        return new EmergencyCall("110");
    }

    /**
     * test on empty entity
     */
    @Test
    public void testEmpty(){
        EmergencyCall call = new EmergencyCall();
        assertEquals("911", call.getDialogNumber());
    }

    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck() {
        EmergencyCall emCall = mockEmCall();
        assertEquals("110", emCall.getDialogNumber());
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        EmergencyCall emCall = mockEmCall();
        emCall.setDialogNumber("119");
        assertEquals("119", emCall.getDialogNumber());
    }
}
