package org.art.java_core.concurrent.design.blocked_buffer;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Shared buffer (bounded blocked queue - FIFO) implementation
 * with the ability of safe mutual multithreaded access.
 */
public class BoundedBlockedBuffer {

    private int maxBufferSize;

    private Deque<Integer> buffer = new LinkedList<>();

    public BoundedBlockedBuffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public synchronized void putInt(Integer value) throws InterruptedException {
        while (buffer.size() == maxBufferSize) {
            System.out.println(Thread.currentThread().getName() + " is waiting, buffer is full.");
            wait();
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        buffer.addFirst(value);
        System.out.println(Thread.currentThread().getName() + " added " + value + ". Thread has notified others.");
        notifyAll();
    }

    public synchronized Integer getInt() throws InterruptedException {
        while (buffer.size() == 0) {
            System.out.println(Thread.currentThread().getName() + " is waiting, buffer is empty.");
            wait();
            System.out.println(Thread.currentThread().getName() + " is awake!");
        }
        Integer value = buffer.getLast();
        buffer.removeLast();
        System.out.println(Thread.currentThread().getName() + " get " + value + ". Thread has notified others.");
        notifyAll();
        return value;
    }
}
