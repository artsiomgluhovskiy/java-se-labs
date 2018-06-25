package org.art.java_core.concurrent.java_util_concurrent.lock.advanced_blocked_buffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared buffer (bounded blocked queue - FIFO) implementation
 * with the ability of safe mutual multithreaded access.
 *
 * Reentrant Lock mechanism with conditions is integrated.
 */
public class BoundedBlockedBuffer {

    private static final Lock LOCK = new ReentrantLock(false);
    private static final Condition EMPTY_CONDITION = LOCK.newCondition();
    private static final Condition FULL_CONDITION = LOCK.newCondition();

    private int maxBufferSize;
    private Deque<Integer> buffer = new LinkedList<>();

    public BoundedBlockedBuffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public void putInt(Integer value) throws InterruptedException {
        try {
            LOCK.lock();
            while (buffer.size() == maxBufferSize) {
                System.out.println(Thread.currentThread().getName() + " is waiting, the buffer is full.");
                FULL_CONDITION.await();
                System.out.println(Thread.currentThread().getName() + " is awake!");
            }
            buffer.addFirst(value);
            System.out.println(Thread.currentThread().getName() + " added " + value + ". Thread has notified others.");
            EMPTY_CONDITION.signalAll();
        } finally {
            LOCK.unlock();
        }
    }

    public Integer getInt() throws InterruptedException {
        try {
            LOCK.lock();
            while (buffer.size() == 0) {
                System.out.println(Thread.currentThread().getName() + " is waiting, the buffer is empty.");
                EMPTY_CONDITION.await();
                System.out.println(Thread.currentThread().getName() + " is awake!");
            }
            Integer value = buffer.getLast();
            buffer.removeLast();
            System.out.println(Thread.currentThread().getName() + " get " + value + ". Thread has notified others.");
            FULL_CONDITION.signalAll();
            return value;
        } finally {
            LOCK.unlock();
        }
    }
}
