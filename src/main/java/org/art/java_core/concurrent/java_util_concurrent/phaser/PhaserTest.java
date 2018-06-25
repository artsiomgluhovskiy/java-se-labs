package org.art.java_core.concurrent.java_util_concurrent.phaser;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

/**
 * Simple Phaser test (Bus trip simulation).
 */
public class PhaserTest {

    private static final Phaser PHASER = new Phaser(1);     //main thread is registered

    public static void main(String[] args) {

        //Phases 0 and 6 - bus park, 1...5 bus stops

        ArrayList<Passenger> passengers = new ArrayList<>();

        //Passengers generating
        for (int i = 1; i < 5; i++) {
            if (((int) (Math.random() * 4)) % 2 == 0) {
                //Destination is intermediate bus stop
                passengers.add(new Passenger(i, i + 1));
            } else {
                //Destination is the final bus stop
                passengers.add(new Passenger(i, 5));
            }
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Phase 0. The bus has left the park.");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Phase 6. The bus has arrived to the park.");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                     int currentBusStop = PHASER.getPhase();
                    System.out.printf("Phase %d. Bus stop №%d", currentBusStop, currentBusStop);

                    for (Passenger p : passengers) {
                        if (p.departure == currentBusStop) {
                            PHASER.register();
                            p.start();
                        }
                    }
                    PHASER.arriveAndAwaitAdvance();
            }
        }
    }

    public static class Passenger extends Thread {

        private int departure;
        private int destination;

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " is waiting on the bus stop № " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " has gotten on the bus.");

                while (PHASER.getPhase() < destination) {
                    PHASER.arriveAndAwaitAdvance();
                }

                Thread.sleep(2);
                System.out.println(this + " has left the bus.");
                PHASER.arriveAndDeregister();
            } catch (InterruptedException e) {
                //NOP
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Passenger{" + departure + " -> " + destination + '}';
        }
    }
}
