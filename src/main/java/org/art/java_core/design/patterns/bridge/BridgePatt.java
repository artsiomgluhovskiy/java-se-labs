package org.art.java_core.design.patterns.bridge;

public class BridgePatt {
    public static void main(String[] args) {
        Car c = new Sedan(new Kia());
        c.showDetails();
    }
}

abstract class Car {
    Make make;
    public Car(Make m) {
        this.make = m;
    }
    abstract void showDetails();
}

class Sedan extends Car {
    public Sedan(Make m){
        super(m);
    }

    @Override
    void showDetails() {
        System.out.println("Sedan");
        make.setMake();
    }
}

class Hatchback extends Car {
    public Hatchback(Make m){
        super(m);
    }

    @Override
    void showDetails() {
        System.out.println("Hatchback");
        make.setMake();
    }
}

interface Make {
    void setMake();
}

class Kia implements Make {
    @Override
    public void setMake() {
        System.out.println("Kia");
    }
}

class Skoda implements Make {
    @Override
    public void setMake() {
        System.out.println("Skoda");
    }
}

