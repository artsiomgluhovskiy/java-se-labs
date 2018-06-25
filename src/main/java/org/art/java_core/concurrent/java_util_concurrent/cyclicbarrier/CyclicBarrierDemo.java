package org.art.java_core.concurrent.java_util_concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier test (simple thread race simulation).
 */
public class CyclicBarrierDemo {

    private static class Racer implements Runnable {

        private CyclicBarrier barrier;

        public Racer(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            new Thread(this, name).start();
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is near the barrier...");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier!");
            } catch (InterruptedException | BrokenBarrierException e) {
                //NOP
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {

        Runnable barrierAction = () -> {
            System.out.println("All threads have pulled up to the barrier.");
            try {
                System.out.println("3!");
                Thread.sleep(1000);
                System.out.println("2!");
                Thread.sleep(1000);
                System.out.println("1!");
                Thread.sleep(1000);
                System.out.println("Go!");
            } catch (InterruptedException e) {
                //NOP
                e.printStackTrace();
            }
        };

        final CyclicBarrier cb = new CyclicBarrier(3, barrierAction);

        new Racer(cb, "Thread 1");
        new Racer(cb, "Thread 2");
        new Racer(cb, "Thread 3");
    }
}

