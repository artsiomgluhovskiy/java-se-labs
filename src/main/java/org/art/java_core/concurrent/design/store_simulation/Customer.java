package org.art.java_core.concurrent.design.store_simulation;

import lombok.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Store customer implementation.
 */
@Data
public class Customer extends Thread {

    private int customerId;
    private ShoppingBasket shoppingBasket;
    private final StoreQueue storeQueue;

    private Random random = new Random(System.nanoTime());

    public Customer(int customerId, StoreQueue storeQueue, ThreadGroup groupName) {
        super(groupName, "Customer_" + customerId);
        this.customerId = customerId;
        this.storeQueue = storeQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " has come to the store...");
        try {
            if (!makePurchase()) {
                System.out.println(Thread.currentThread().getName() + " is going home.");
                return;
            }
            goToQueue();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted!");
        }
    }

    private boolean makePurchase() throws InterruptedException {
        Thread.sleep((random.nextInt(50) + 1) * 40);
        shoppingBasket = new ShoppingBasket();
        if(shoppingBasket.getListOfGoods() == null) {
            System.out.println(Thread.currentThread().getName() + " has chosen nothing.");
            return false;
        } else {
            System.out.println(Thread.currentThread().getName() + " has made a purchase.\n" + Thread.currentThread().getName() + " box: " + shoppingBasket);
            return true;
        }
    }

    private void goToQueue() throws InterruptedException {
        Thread.sleep((random.nextInt(50) + 1) * 20);
        storeQueue.enqueueCustomer(this);
    }
}
