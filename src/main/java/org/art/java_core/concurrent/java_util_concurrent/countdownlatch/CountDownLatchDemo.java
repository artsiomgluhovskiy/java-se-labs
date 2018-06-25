package org.art.java_core.concurrent.java_util_concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch test (car race simulation).
 */
public class CountDownLatchDemo {

    private static final CountDownLatch START = new CountDownLatch(8);

    //The length of the race track
    private static final int TRACK_LENGTH = 500000;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            Thread.sleep(1000);
        }

        while (START.getCount() > 3) {
            Thread.sleep(100);
        }

        Thread.sleep(1000);
        System.out.println("Ready!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Steady!");
        START.countDown();
        Thread.sleep(1000);
        System.out.println("Go!");
        START.countDown();          //The counter value equals to 8. The barrier opens!
    }

    public static class Car implements Runnable {

        private int carNumber;
        private int carSpeed;   //The speed of the car is uniform

        public Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("The car №%d pulled up to the start line.\n", carNumber);
                START.countDown();
                START.await();      //We are blocking here until all cars pull up to the start line
                Thread.sleep(TRACK_LENGTH / carSpeed);  //The car is running
                System.out.printf("The car №%d has finished! Average speed is: %d\n", carNumber, carSpeed);
            } catch (InterruptedException e) {
                //NOP
                e.printStackTrace();
            }
        }
    }
}
