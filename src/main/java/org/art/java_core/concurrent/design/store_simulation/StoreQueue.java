package org.art.java_core.concurrent.design.store_simulation;

import lombok.Data;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * Store queue implementation (bounded blocked buffer).
 */
@Data
public class StoreQueue {

    private int maxQueueSize;
    private final Deque<Customer> storeQueue = new LinkedList<>();

    private Random random = new Random(System.nanoTime());

    public StoreQueue(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public synchronized void enqueueCustomer(Customer customer) throws InterruptedException {
        while (maxQueueSize == storeQueue.size()) {
            System.out.println("The queue is overflowed! " + Thread.currentThread().getName() + " has to wait...");
            wait();
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        storeQueue.addLast(customer);
        System.out.println(Thread.currentThread().getName() + " has stayed to the queue.");
        notifyAll();
    }

    public synchronized Customer takeCustomerFromQueue() throws InterruptedException {
        Customer customer;
        while (storeQueue.size() == 0) {
            System.out.println("The queue is empty! " + Thread.currentThread().getName() + " has to wait...");
            wait();
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        customer = storeQueue.removeFirst();
        System.out.println(Thread.currentThread().getName() + " has taken a customer " + customer.getCustomerId() + " from the queue.");
        notifyAll();
        return customer;
    }

    public synchronized void notifyCashboxes() {
        notifyAll();
    }
}
