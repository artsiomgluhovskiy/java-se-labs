package org.art.java_core.design.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer pattern (from GoF) - simple code example.
 * Weather station simulation.
 */
public class Observer {

    public static void main(String[] args) {

        WeatherStation station = new WeatherStation();
        station.addObserver(new ConsoleObserver("Observer 1"));
        station.addObserver(new ConsoleObserver("Observer 2"));
        station.setMeasurements(25, 760);
    }
}

interface ObservableIF {

    void addObserver(ObserverIF o);

    void removeObserver(ObserverIF o);

    void notifyObservers();
}

class WeatherStation implements ObservableIF {

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

    void handleEvent(int temp, int pressure);
}

class ConsoleObserver implements ObserverIF {

    private String identifier;

    public ConsoleObserver(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void handleEvent(int temp, int pressure) {
        System.out.printf("Notified: %s. The weather changed. Temperature: %s, pressure: %s.%n", this.identifier, temp, pressure);
    }

    public String getIdentifier() {
        return identifier;
    }
}