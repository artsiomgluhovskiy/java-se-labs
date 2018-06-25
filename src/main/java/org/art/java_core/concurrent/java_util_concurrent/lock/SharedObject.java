package org.art.java_core.concurrent.java_util_concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * The object shared between some thread with specified sync rules.
 */
public class SharedObject {

    private Lock lock;
    private Condition heavyTypeCondition;
    private Condition lightTypeCondition;

    public SharedObject(Lock lock) {
        this.lock = lock;

        //Waited set for workers with heavy type
        this.heavyTypeCondition = lock.newCondition();

        //Waited set for workers with light type
        this.lightTypeCondition = lock.newCondition();
    }

    /**
     * All workers with 'HEAVY' and 'LIGHT' type are waiting on the
     * first stage until the 'MAIN' worker goes to the second stage
     * and signals 'HEAVY' worker at first and then 'HEAVY' workers
     * signals 'LIGHT' workers.
     */
    public void firstWorkStage(Worker worker) {
        try {
            lock.lock();
            System.out.printf("Worker %d with %s-type is on the FIRST stage...%n", worker.getWorkerId(), worker.getWorkerType());
            switch (worker.getWorkerType()) {
                case LIGHT:
                    System.out.printf("Worker %d with %s-type is on the FIRST stage and waiting...%n",
                            worker.getWorkerId(), worker.getWorkerType());
                    lightTypeCondition.await();
                    System.out.printf("Worker %d with %s-type is on the FIRST stage and awakes...%n",
                            worker.getWorkerId(), worker.getWorkerType());
                    break;
                case HEAVY:
                    System.out.printf("Worker %d with %s-type is on the FIRST stage and waiting...%n",
                            worker.getWorkerId(), worker.getWorkerType());
                    heavyTypeCondition.await();
                    System.out.printf("Worker %d with %s-type is on the FIRST stage and awakes...%n",
                            worker.getWorkerId(), worker.getWorkerType());
                    break;
            }
            secondWorkStage(worker);
        } catch (InterruptedException e) {
            //NOP
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void secondWorkStage(Worker worker) {
        lock.lock();
        System.out.printf("Worker %d with %s-type is on the SECOND stage...%n", worker.getWorkerId(), worker.getWorkerType());
        if (worker.getWorkerType() == Worker.Type.MAIN) {
            //releasing 'HEAVY' workers
            System.out.printf("Worker %d with %s-type is on the SECOND stage and releases 'HEAVY' workers!%n",
                    worker.getWorkerId(), worker.getWorkerType());
            heavyTypeCondition.signalAll();
        } else if (worker.getWorkerType() == Worker.Type.HEAVY) {
            //releasing 'LIGHT' workers
            System.out.printf("Worker %d with %s-type is on the SECOND stage and releases 'LIGHT' workers!%n",
                    worker.getWorkerId(), worker.getWorkerType());
            lightTypeCondition.signalAll();
        }
        lock.unlock();
    }
}
