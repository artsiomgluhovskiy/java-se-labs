package org.art.java_core.design.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern (from GoF) - simple code example.
 */
public class Observer {

    public static void main(String[] args) {

        MeteoStation station = new MeteoStation();
        station.addObserver(new ConsoleObserver());
        station.setMeasurements(25, 760);
    }
}

interface ObservableIF {

    void addObserver(ObserverIF o);

    void removeObserver(ObserverIF o);

    void notifyObservers();
}

class MeteoStation implements ObservableIF {

    private int temperature;
    private int pressure;

    private List<ObserverIF> observers = new ArrayList<>();

    public void setMeasurements(int t, int p) {
        temperature = t;
        pressure = p;
        notifyObservers();
    }

    @Override
    public void addObserver(ObserverIF o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ObserverIF o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach((ObserverIF o) -> o.handleEvent(temperature, pressure));
    }
}

interface ObserverIF {

    void handleEvent(int temp, int presser);
}

class ConsoleObserver implements ObserverIF {

    @Override
    public void handleEvent(int temp, int presser) {
        System.out.println("The weather changed. Temperature: " + temp + ", pressure: " + presser);
    }
}