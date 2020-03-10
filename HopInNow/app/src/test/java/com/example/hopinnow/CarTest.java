package com.example.hopinnow;

import com.example.hopinnow.entities.Car;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    // set up test entity
    private Car mockCar(){
        Car mockCar = new Car("Nissan", "Altima", "Black", "AAA-0001");
        return mockCar;
    }
    // test on creating new entity
    @Test
    public void testNew() {
        Car newCar = new Car("make", "model", "color", "plate");
        assertEquals("make", newCar.getMake());
        assertEquals("model", newCar.getModel());
        assertEquals("color", newCar.getColor());
        assertEquals("plate", newCar.getPlateNumber());
    }
    // test on checking entity elements
    @Test
    public void testCheck(){
        Car newCar = mockCar();
        assertEquals("Nissan", newCar.getMake());
        assertEquals("Altima", newCar.getModel());
        assertEquals("Black", newCar.getColor());
        assertEquals("AAA-0001", newCar.getPlateNumber());
    }
    // test on modifying entity elements
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
