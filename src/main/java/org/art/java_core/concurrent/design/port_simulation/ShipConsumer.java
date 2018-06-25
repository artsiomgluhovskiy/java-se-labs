package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ship consumer implementation.
 */
@Data
@ToString(exclude = {"isInter", "port"})
public class ShipConsumer extends Thread {

    private int storageCapacity;
    private volatile boolean isInter;
    private Port port;
    private Pier myPier;
    private List<Container> shipStorage = new ArrayList<>();

    private Random random = new Random(System.nanoTime());

    public ShipConsumer(Port port, String name) {
        super(name);
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " ship is running...");
        initStorage();
        System.out.println(Thread.currentThread().getName() + " ship is ready. Ship info: " + this);
        while (!isInterrupted()) {
            try {
                myPier = port.findAppropriatePier();
                if (myPier == null) {
                    sleep(250);
                    continue;
                }
                int contNum = 0;
                while (contNum < storageCapacity && !myPier.isTempStorageEmpty()) {
                    shipStorage.add(myPier.getContainers());
                    contNum++;
                }
                while (contNum < storageCapacity && !isInterrupted()) {
                    shipStorage.add(port.getPortStorage().getContainer());
                    contNum++;
                }
                myPier.setOccupied(false);
                System.out.println(Thread.currentThread().getName() + " ship has loaded and now is returning to the base. Ship info: " + this);
                shipStorage.clear();
                System.out.println(Thread.currentThread().getName() + " ship has cleared his storage." + this);
                sleep(350);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
                return;
            }
        }
    }

    private void initStorage() {
        storageCapacity = random.nextInt(5) + 1;
        shipStorage = new ArrayList<>(storageCapacity);
    }
}

