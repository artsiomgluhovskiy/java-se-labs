package org.art.java_core.concurrent.java_util_concurrent.semaphore;

public class ItemConsumer extends Thread {

    private int consumerId;
    private Pool<Item> pool;

    public ItemConsumer(int consumerId, Pool<Item> pool) {
        this.consumerId = consumerId;
        this.pool = pool;
    }

    @Override
    public void run() {
        System.out.printf("ItemConsumer %d is running...%n", consumerId);
        while (!isInterrupted()) {
            try {
                Thread.sleep(500);
                //Attempts to retrieve an item from the pool
                Item item = pool.getItem();
                if (item != null) {
                    System.out.printf("ItemConsumer %d took the item %d from the pool.%n", consumerId, item.getItemId());
                } else {
                    continue;
                }

                Thread.sleep(2000);     //Some payload here

                //Returning the item to the pool
                pool.returnItem(item);
                System.out.printf("ItemConsumer %d returned the item %s to the pool.%n", consumerId, item.getItemId());
            } catch (InterruptedException e) {
                System.out.printf("ItemConsumer %d has been terminated!%n", consumerId);
                return;
            }
        }
    }
}
