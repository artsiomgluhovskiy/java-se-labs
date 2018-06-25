package org.art.java_core.concurrent.scheduling;

/**
 * Simple job implementation.
 */
public class Job extends Thread {

    private int time;

    public Job(int time, String name) {
        super(name);
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": I have been sleeping for " + time + " seconds!");

        //Payload here
    }
}
