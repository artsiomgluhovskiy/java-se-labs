package org.art.java_core.concurrent.java_util_concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple Reentrant Lock Demo (with conditions).
 *
 * All workers with 'HEAVY' and 'LIGHT' types are waiting on the
 * first stage of shared object until the 'MAIN' worker goes to
 * the second stage and signals 'HEAVY' worker at first and then
 * 'HEAVY' workers signals 'LIGHT' workers.
 */
public class ReentrantLockTest {

    public static void main(String[] args) throws InterruptedException {

        SharedObject sharedObject = new SharedObject(new ReentrantLock(false));

        //Workers initialization and running
        Thread[] workers = {
                new Worker(1, Worker.Type.HEAVY, sharedObject),
                new Worker(2, Worker.Type.LIGHT, sharedObject),
                new Worker(3, Worker.Type.HEAVY, sharedObject),
                new Worker(4, Worker.Type.LIGHT, sharedObject),
                new Worker(5, Worker.Type.HEAVY, sharedObject),
                //NOTE: The last worker has 'MAIN' type!
                new Worker(5, Worker.Type.MAIN, sharedObject),
        };
        for (Thread worker : workers) {
            if (((Worker)worker).getWorkerType() == Worker.Type.MAIN) {
                Thread.sleep(4000);
            }
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }
        System.out.println("*** The process has been terminated! ***");
    }
}
