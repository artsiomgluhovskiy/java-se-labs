package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ship producer implementation.
 */
@Data
@ToString(exclude = {"isInter", "port"})
public class ShipProducer extends Thread {

    private int storageCapacity;
    private volatile boolean isInter;
    private Port port;
    private Pier myPier;
    private ArrayList<Container> shipStorage;

    private Random random = new Random(System.nanoTime());

    public ShipProducer(Port port, String name) {
        super(name);
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " ship is running...");
        initStorage();
        System.out.println(Thread.currentThread().getName() + " ship is ready.");
        boolean isContinued = false;
        while (!isInterrupted()) {
            if (!isContinued) {
                fillStorage();
                System.out.println("Producer ship info: " + this);
            }
            try {
                myPier = port.findAppropriatePier();
                if (myPier == null) {
                    isContinued = true;
                    sleep(150);
                    continue;
                }
                isContinued = false;
                int contNum = 0;
                while (!myPier.isTempStorageFull() && contNum < shipStorage.size()) {
                    myPier.putContainers(shipStorage.get(contNum));
                    contNum++;
                }
                while (contNum < shipStorage.size() && !isInterrupted()) {
                    port.getPortStorage().putContainer(shipStorage.get(contNum));
                    contNum++;
                }
                myPier.setOccupied(false);
                shipStorage.clear();
                System.out.println(Thread.currentThread().getName() + " ship has been emptied and now is returning to the base. Ship info: " + this);
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

    private void fillStorage() {
        for (int i = 0; i < storageCapacity; i++) {
            shipStorage.add(new Container(random.nextInt(50) + 1));
        }
    }
}
