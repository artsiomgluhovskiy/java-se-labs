package org.art.java_core.concurrent.java_util_concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Exchanger test.
 */
public class ExchangerTest {

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {

        //Delivery (the second parcel in each delivery should be exchanged between the trucks)
        //'E' - the point of exchange
        String[] p1 = new String[]{"{delivery A->D}", "{delivery A->C}"};   //delivery for the first truck
        String[] p2 = new String[]{"{delivery B->C}", "{delivery A->D}"};   //delivery for the second truck

        new Thread(new Truck(1, "A", "D", p1)).start();     //the first truck goes from А to D

        Thread.sleep(100);

        new Thread(new Truck(2, "B", "C", p2)).start();     //the second truck goes from В to С
    }

    public static class Truck implements Runnable {

        private int truckId;
        private String departure;
        private String destination;
        private String[] parcels;

        public Truck(int truckId, String departure, String destination, String[] parcels) {
            this.truckId = truckId;
            this.departure = departure;
            this.destination = destination;
            this.parcels = parcels;
        }

        @Override
        public void run() {
            try {
                System.out.printf("The truck №%d was loaded with: %s and %s.\n", truckId, parcels[0], parcels[1]);
                System.out.printf("The truck №%d goes from point '%s' to point '%s'.\n", truckId, departure, destination);
                Thread.sleep(1000 + (long) (Math.random() * 5000));
                System.out.printf("The truck №%d has arrived to point 'Е' and ready to exchange parcels.\n", truckId);
                parcels[1] = EXCHANGER.exchange(parcels[1]);    //The thread is blocking here until another thread invokes 'exchange()'
                System.out.printf("The delivery was transferred to the truck №%d for point '%s'.\n", truckId, destination);
                Thread.sleep(1000 + (long) (Math.random() * 5000));
                System.out.printf("The truck №%d has arrived to point '%s' and delivered: '%s' и '%s'.\n", truckId, destination, parcels[0], parcels[1]);
            } catch (InterruptedException e) {
                //NOP
                e.printStackTrace();
            }
        }
    }
}