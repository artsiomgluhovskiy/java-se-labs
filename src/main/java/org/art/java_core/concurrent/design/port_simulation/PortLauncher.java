package org.art.java_core.concurrent.design.port_simulation;

/**
 * Sea port simulation test.
 */
public class PortLauncher {

    public static void main(String[] args) throws InterruptedException {

        Port port = new Port(2, 2);

        Thread[] ships = {
                new ShipProducer(port, "Producer"),
                new ShipConsumer(port, "Consumer")
        };

        for (Thread t : ships) {
            t.start();
        }

        Thread.sleep(3000);

        //Finishing process
        for (Thread t : ships) {
            t.interrupt();
        }

        for (Thread t : ships) {
            t.join();
        }

        System.out.println("*** Threads were interrupted!!! ***");
    }
}
