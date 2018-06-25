package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

/**
 * Sea pier implementation (with temporary storage).
 */
@Data
@ToString(exclude = {"port", "isOccupied"})
public class Pier {

    private int tempStorageCapacity;
    private int pierId;
    private volatile boolean isOccupied = false;
    private Port port;
    private ArrayList<Container> tempPierStorage;

    public Pier(Port port, int pierId, int tempStorageCapacity) {
        this.tempStorageCapacity = tempStorageCapacity;
        this.pierId = pierId;
        this.port = port;
        this.tempPierStorage = new ArrayList<>(tempStorageCapacity);
    }

    public synchronized void putContainers(Container cont) {
        tempPierStorage.add(cont);
        System.out.println(Thread.currentThread().getName() + " stores " + cont + " in the pier storage. Pier info: " + this);
    }

    public synchronized Container getContainers() {
        Container container = tempPierStorage.get(0);
        tempPierStorage.remove(0);
        System.out.println(Thread.currentThread().getName() + " gets " + container + " in the pier storage. Pier info: " + this);
        return container;
    }

    public boolean isTempStorageFull() {
        return tempStorageCapacity == tempPierStorage.size();
    }

    public boolean isTempStorageEmpty() {
        return tempPierStorage.size() == 0;
    }
}
