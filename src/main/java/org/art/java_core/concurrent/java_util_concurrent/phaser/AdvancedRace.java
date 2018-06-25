package org.art.java_core.concurrent.java_util_concurrent.phaser;

import java.util.concurrent.Phaser;

/**
 * Advanced version of the car race simulation (with Phaser integration)
 */
public class AdvancedRace {

    private static final Phaser START = new Phaser(8);

    //The length of the race track
    private static final int TRACK_LENGTH = 500000;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(100);
        }

        while (START.getRegisteredParties() > 3) {
            Thread.sleep(100);
        }

        Thread.sleep(100);
        System.out.println("Ready!");
        START.arriveAndDeregister();
        Thread.sleep(100);
        System.out.println("Steady!");
        START.arriveAndDeregister();
        Thread.sleep(100);
        System.out.println("Go!");
        START.arriveAndDeregister();
    }

    public static class Car implements Runnable {

        private int carNumber;
        private int carSpeed;

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("The car №%d pulled up to the start line.%n", carNumber);
                START.arriveAndDeregister();
                START.awaitAdvance(0);
                Thread.sleep(TRACK_LENGTH / carSpeed);
                System.out.printf("The car №%d has finished! Average speed is: %d%n", carNumber, carSpeed);
            } catch (InterruptedException e) {
                //NOP
                e.printStackTrace();
            }
        }
    }
}
