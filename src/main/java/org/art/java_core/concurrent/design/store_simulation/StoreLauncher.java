package org.art.java_core.concurrent.design.store_simulation;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StoreLauncher {

    public static void main(String[] args) throws InterruptedException {

        StoreQueue store = new StoreQueue(1);
        ThreadGroup customersGroup = new ThreadGroup("Customers");
        ThreadGroup cashboxesGroup = new ThreadGroup("Cashboxes");

        Random random = new Random(System.nanoTime());

        Thread[] cashboxes = new Cashbox[random.nextInt(4) + 2];
        Thread[] customers = new Customer[random.nextInt(41) + 10];

        System.out.println("Initial info: amount of cashboxes: " + cashboxes.length + ", amount of customers: " + customers.length);

        //Cashboxes generating and launching
        for (int i = 0; i < cashboxes.length; i++) {
            cashboxes[i] = new Cashbox(i + 1, store, cashboxesGroup);
            cashboxes[i].start();
            TimeUnit.SECONDS.sleep(1);
        }

        //Customer generating and launching
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(i + 1, store, customersGroup);
            customers[i].start();
            TimeUnit.SECONDS.sleep(1);
        }

        TimeUnit.SECONDS.sleep(3);

        //Cashboxes termination
        System.out.println("******************* Process termination! *******************");
        System.out.println("Active customers number: " + customersGroup.activeCount());
        System.out.println("Store queue size: " + store.getStoreQueue().size());

        for (Thread t : cashboxes) {
            t.interrupt();
        }

        for (Thread t : customers) {
            t.interrupt();
        }

        for (Thread t : cashboxes) {
            t.join();
        }

        for (Thread t : customers) {
            t.join();
        }

        System.out.println("*** The process has been terminated! ***");
    }
}
