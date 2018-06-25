package org.art.java_core.concurrent.java_util_concurrent.lock.advanced_blocked_buffer;

/**
 * Consumer/producer pattern test (with the integration of Reentrant Lock mechanism).
 */
public class ConsumerProducerTest {

    public static void main(String[] args) throws InterruptedException {

        BoundedBlockedBuffer buffer = new BoundedBlockedBuffer(2);

        Thread[] actors = {
                new Producer("Producer1", buffer),
                new Consumer("Consumer1", buffer),
                new Producer("Producer2", buffer)
        };

        //Threads launching
        for (Thread t : actors) {
            t.start();
        }

        Thread.sleep(2500);

        //Threads interruption
        for (Thread t : actors) {
            t.interrupt();
        }

        for (Thread t : actors) {
            t.join();
        }

        System.out.println("*** The process is finished ***");
    }
}
