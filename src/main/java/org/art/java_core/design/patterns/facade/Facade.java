package org.art.java_core.design.patterns.facade;

/**
 * Facade pattern (from GoF) - simple code example.
 * Simple computer object model simulation.
 */
public class Facade {

    public static void main(String[] args) {

        Computer computer = new Computer();
        computer.launch();
        computer.shutDown();
    }
}

//Complex system with incapsulated initialization/closing logic
class Computer {

    private Power power = new Power();

    private DVDRom dvd = new DVDRom();

    private HDD hdd = new HDD();

    public void launch() {
        //Some complicated initialization logic here
        power.on();
        if (dvd.hasData()) {
            dvd.load();
        }
        hdd.copyFromDVD(dvd);
    }

    public void shutDown() {
        //Some complicated 'shut down' logic here
        if (dvd.hasData()) {
            dvd.unload();
        }
        power.off();
    }
}

class Power {

    void on() {
        System.out.println("Computer launching...");
    }

    void off() {
        System.out.println("Computer shut down...");
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

    public void unload() {
        data = false;
    }
}

class HDD {

    void copyFromDVD(DVDRom dvd) {
        if (dvd.hasData()) {
            System.out.println("Data is copying...");
        } else {
            System.out.println("Insert disk with data!");
        }
    }
}
