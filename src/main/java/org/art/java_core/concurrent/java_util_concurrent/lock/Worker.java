package org.art.java_core.concurrent.java_util_concurrent.lock;

/**
 * Simple worker entity implementation.
 */
public class Worker extends Thread {

    enum Type {
        LIGHT, HEAVY, MAIN
    }

    private int workerId;
    private Type workerType;
    private SharedObject sharedObject;

    public Worker(int workerId, Type workerType, SharedObject sharedObject) {
        this.workerId = workerId;
        this.workerType = workerType;
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        System.out.printf("Worker %d with %s-type is running...%n", workerId, workerType);
        sharedObject.firstWorkStage(this);
        System.out.printf("Worker %d with %s-type has finished...%n", workerId, workerType);
    }

    public int getWorkerId() {
        return workerId;
    }

    public Type getWorkerType() {
        return workerType;
    }
}
