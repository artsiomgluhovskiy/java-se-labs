package org.art.java_core.concurrent.java_util_concurrent.lock.advanced_blocked_buffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Producer thread implementation.
 */
public class Producer extends Thread {

    public static final Logger log = LogManager.getLogger(Producer.class);

    private Random random = new Random(System.nanoTime());

    private BoundedBlockedBuffer buffer;

    public Producer(String threadName, BoundedBlockedBuffer buffer) {
        super(threadName);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.putInt(random.nextInt(50));
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " has been interrupted!");
                return;
            }
        }
    }
}
