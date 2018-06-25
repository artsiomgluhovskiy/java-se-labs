package org.art.java_core.concurrent.java_util_concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Object Pool implementation (with Semaphore integration).
 *
 * @param <T> - generic type of contained objects.
 */
public class Pool<T> {

    private int poolCapacity;
    private boolean[] used;
    private T[] items;
    private Semaphore semaphore;

    public Pool(T[] items) {
        this.items = items;
        this.poolCapacity = items.length;
        this.used = new boolean[poolCapacity];
        this.semaphore = new Semaphore(poolCapacity, true);
    }

    public T getItem() throws InterruptedException {
        semaphore.acquire();
        return getNextAvailableItem();
    }

    public void returnItem(T item) {
        if (markAsUnused(item)) {
            semaphore.release();
        }
    }

    private synchronized T getNextAvailableItem() {
        for (int i = 0; i < poolCapacity; ++i) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null;
    }

    private synchronized boolean markAsUnused(Object item) {
        for (int i = 0; i < poolCapacity; ++i) {
            if (item == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }
}
