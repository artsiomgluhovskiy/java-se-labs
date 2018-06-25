package org.art.java_core.concurrent.parallel_processing;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Parallel max value finder implementation.
 */
public class ParallelMaxFinder implements Callable<Integer> {

    private int[] intArray;
    private int from;
    private int to;

    public ParallelMaxFinder(int[] intArray, int from, int to) {
        this.intArray = intArray;
        this.from = from;
        this.to = to;
    }

    @Override
    public Integer call() throws InterruptedException {
        int numOfOperations = 0;
        int maxValue = Integer.MIN_VALUE;
        for (int i = from; i < to; i++) {
            if (intArray[i] > maxValue) {
                maxValue = intArray[i];
            }
            numOfOperations++;

            //Artificial load to make results more demonstrative
            TimeUnit.MICROSECONDS.sleep(10);
        }
        System.out.println(Thread.currentThread().getName() + ": number of operations - "
                + numOfOperations + ". Max value: " + maxValue);
        return maxValue;
    }
}
