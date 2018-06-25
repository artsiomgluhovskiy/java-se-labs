package org.art.java_core.concurrent.design.port_simulation;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Sea port implementation with piers and port storage.
 */
@Data
@ToString
public class Port {

    private final List<Pier> piers;
    private final PortStorage portStorage;

    public Port(int piersNumber, int storageSize) {
        this.piers = generatePiers(piersNumber);
        this.portStorage = new PortStorage(storageSize);
    }

    public synchronized Pier findAppropriatePier() {
        for (Pier p : piers) {
            if (!p.isOccupied()) {
                p.setOccupied(true);
                System.out.println(Thread.currentThread().getName() + " has found an appropriate pier. Pier info: " + p);
                return p;
            }
        }
        System.out.println(Thread.currentThread().getName() + " can't find a pier. All piers are occupied at the moment.");
        return null;
    }

    private List<Pier> generatePiers(int piersNumber) {
        List<Pier> piers = new ArrayList<>(piersNumber);
        for (int i = 0; i < piersNumber; i++) {
            piers.add(new Pier(this, i + 1, 2));
        }
        return piers;
    }
}
