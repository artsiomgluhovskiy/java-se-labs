package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Port storage implementation (bounded blocked buffer).
 */
@Data
@ToString
public class PortStorage {

    private int portStorageSize;
    private Deque<Container> portStorage = new LinkedList<>();

    public PortStorage(int portStorageSize) {
        this.portStorageSize = portStorageSize;
    }

    public synchronized void putContainer(Container container) throws InterruptedException {
        while (portStorageSize == portStorage.size()) {
            //Storage is full
            System.out.println(Thread.currentThread().getName() + " is waiting, port storage is full...");
            wait(1500);
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        portStorage.addFirst(container);
        System.out.println(Thread.currentThread().getName() + " puts container (id = "
                + container.getId() + ") into the port storage and notifies another ship.");
        System.out.println("Port info: " + this);
        notify();
    }

    public synchronized Container getContainer() throws InterruptedException {
        while (portStorage.size() == 0) {
            //Storage is empty
            System.out.println(Thread.currentThread().getName() + " is waiting, port storage is empty...");
            wait(1500);
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        Container container = portStorage.getLast();
        portStorage.removeLast();
        System.out.println(Thread.currentThread().getName() + " gets container (id = " + container.getId()
                + ") from the port storage and notifies another ship.");
        notify();
        return container;
    }
}
