package org.art.java_core.concurrent.java_util_concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Semaphore Deadlock demo.
 */
public class DeadlockDemo {

    public static void main(String[] args) throws Exception {

        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);

        Thread t = new Thread(new DoubleResourceGrabber(s1, s2));
        Thread t2 = new Thread(new DoubleResourceGrabber(s2, s1));

        t.start();
        t2.start();
        t.join();
        t2.join();

        System.out.println("*** The process has accomplished successfully! ***");
    }

    /**
     * Tries to take resources from both semaphores without its releasing.
     */
    private static class DoubleResourceGrabber implements Runnable {

        private Semaphore first;
        private Semaphore second;

        public DoubleResourceGrabber(Semaphore s1, Semaphore s2) {
            first = s1;
            second = s2;
        }

        public void run() {
            try {
                first.acquire();
                System.out.println(Thread.currentThread() + " acquired " + first);
                Thread.sleep(400); // deadlock
                second.acquire();
                System.out.println(Thread.currentThread() + " acquired " + second);
                second.release();
                System.out.println(Thread.currentThread() + " released " + second);
                first.release();
                System.out.println(Thread.currentThread() + " released " + first);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
