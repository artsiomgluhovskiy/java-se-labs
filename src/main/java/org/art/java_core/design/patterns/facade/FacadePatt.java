package org.art.java_core.design.patterns.facade;

public class FacadePatt {
    public static void main(String[] args) {

        Computer computer = new Computer();
        computer.copy();

    }
}

class Computer {
    Power power = new Power();
    DVDRom dvd = new DVDRom();
    HDD hdd = new HDD();

    public void copy() {
        power.on();
        dvd.load();
        hdd.copyFromDVD(dvd);
    }
}

class Power {
    void on() {
        System.out.println("Power on!");
    }

    void off() {
        System.out.println("Power off!");
    }
}

class DVDRom {
    private boolean data = false;

    public boolean hasData() {
        return data;
    }

    public void load() {
        data = true;
    }

    public void unLoad() {
        data = false;
    }
}

class HDD {
    void copyFromDVD(DVDRom dvd) {
        if (dvd.hasData() == true) {
            System.out.println("Data is copying!");
        } else {
            System.out.println("Insert disk with data!");
        }
    }
}
