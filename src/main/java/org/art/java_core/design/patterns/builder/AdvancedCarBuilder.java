package org.art.java_core.design.patterns.builder;

/**
 * Builder pattern (from GoF) - simple code example.
 * Modified car builder simulation (with 'Director').
 */
public class AdvancedCarBuilder {

    public static void main(String[] args) {

        Director director = new Director();
        director.setBuilder(new SubaruBuilder());
        Car car = director.buildCar();
        System.out.println(car);
    }
}

abstract class CarBuilderBase {
    Car car;

    void createCar() {
        car = new Car();
    }

    abstract void buildMake();
    abstract void buildTransmission();
    abstract void buildMaxSpeed();

    Car getCar() {
        return car;
    }
}

class FordMondeoBuilder extends CarBuilderBase {

    @Override
    void buildMake() {
        car.setMake("Ford");
    }

    @Override
    void buildTransmission() {
        car.setTransmission(Transmission.AUTO);
    }

    @Override
    void buildMaxSpeed() {
        car.setMaxSpeed(120);
    }
}

class SubaruBuilder extends CarBuilderBase {

    @Override
    void buildMake() {
        car.setMake("Subaru");
    }

    @Override
    void buildTransmission() {
        car.setTransmission(Transmission.MANUAL);
    }

    @Override
    void buildMaxSpeed() {
        car.setMaxSpeed(220);
    }
}

class Director {
    private CarBuilderBase builder;
    void setBuilder(CarBuilderBase b) {
        builder = b;
    }
    Car buildCar() {
        builder.createCar();
        builder.buildMake();
        builder.buildTransmission();
        builder.buildMaxSpeed();
        return builder.getCar();
    }
}





