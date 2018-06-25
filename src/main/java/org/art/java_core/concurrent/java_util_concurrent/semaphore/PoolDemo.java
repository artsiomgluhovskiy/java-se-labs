package org.art.java_core.concurrent.java_util_concurrent.semaphore;

/**
 * Custom Object Pool test (Semaphore integration demo).
 */
public class PoolDemo {

    public static void main(String[] args) throws InterruptedException {

        int poolCapacity = 3;

        //Pool initialization
        Item[] items = new Item[poolCapacity];
        for (int i = 0; i < poolCapacity; i++) {
            items[i] = new Item(i);
        }
        Pool<Item> pool = new Pool<>(items);

        //Item consumer generation and running
        Thread[] itemConsumers = new Thread[poolCapacity + 1];
        for (int i = 0; i < itemConsumers.length; i++) {
            itemConsumers[i] = new ItemConsumer(i, pool);
            itemConsumers[i].start();
        }

        Thread.sleep(10000);

        //Process termination
        for (Thread consumer : itemConsumers) {
            consumer.interrupt();
            consumer.join();
        }
        System.out.println("*** Process has been terminated! ***");
    }
}
