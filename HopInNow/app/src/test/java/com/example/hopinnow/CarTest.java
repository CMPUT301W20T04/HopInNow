package com.example.hopinnow;

import com.example.hopinnow.entities.Car;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Author: Hongru Qi
 * Version: 1.0.0
 * car entity test
 */
public class CarTest {
    /**
     * set up test entity
      */
    private Car mockCar(){
        return new Car("Nissan", "Altima", "Black", "AAA-0001");
    }

    /**
     * test on empty class
     */
    @Test
    public void testEmpty() {
        Car car = new Car();
        assertThrows(NullPointerException.class, () -> {
            car.getMake();
        });
        assertThrows(NullPointerException.class, () -> {
            car.getColor();
        });
        assertThrows(NullPointerException.class, () -> {
            car.getModel();
        });
        assertThrows(NullPointerException.class, () -> {
            car.getPlateNumber();
        });
    }

    /**
     * test on creating new entity
     */
    @Test
    public void testNew() {
        Car newCar = new Car("make", "model", "color", "plate");
        assertEquals("make", newCar.getMake());
        assertEquals("model", newCar.getModel());
        assertEquals("color", newCar.getColor());
        assertEquals("plate", newCar.getPlateNumber());
    }

    /**
     * test on checking entity elements
     */
    @Test
    public void testCheck(){
        Car newCar = mockCar();
        assertEquals("Nissan", newCar.getMake());
        assertEquals("Altima", newCar.getModel());
        assertEquals("Black", newCar.getColor());
        assertEquals("AAA-0001", newCar.getPlateNumber());
    }

    /**
     * test on modifying entity elements
     */
    @Test
    public void testEdit() {
        Car car = mockCar();
        car.setMake("newMake");
        assertEquals("newMake", car.getMake());
        car.setModel("newModel");
        assertEquals("newModel", car.getModel());
        car.setColor("newColor");
        assertEquals("newColor", car.getColor());
        car.setPlateNumber("newPlate");
        assertEquals("newPlate", car.getPlateNumber());
    }
}
