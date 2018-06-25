package org.art.java_core.design.patterns.builder;

/**
 * Builder pattern (from GoF) - simple code example.
 * Simple car builder simulation.
 */
public class SimpleCarBuilder {

    public static void main(String[] args) {

        Car car = new CarBuilder()
                .buildMake("Mercedes")
                .buildTransmission(Transmission.AUTO)
                .buildMaxSpeed(120)
                .build();

        System.out.println(car);
    }
}

enum Transmission {
    MANUAL, AUTO
}

class Car {

    private String make;
    private Transmission transmission;
    private int maxSpeed;

    void setMake(String make) {
        this.make = make;
    }

    void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", transmission=" + transmission +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}

class CarBuilder {

    private String m = "Default";
    private Transmission t = Transmission.MANUAL;
    private int s = 120;

    CarBuilder buildMake(String m) {
        this.m = m;
        return this;
    }

    CarBuilder buildTransmission(Transmission t) {
        this.t = t;
        return this;
    }

    CarBuilder buildMaxSpeed(int s) {
        this.s = s;
        return this;
    }

    Car build() {
        Car car = new Car();
        car.setMake(m);
        car.setTransmission(t);
        car.setMaxSpeed(s);
        return car;
    }
}
