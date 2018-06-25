package org.art.java_core.concurrent.parallel_processing;

import java.util.Random;
import java.util.concurrent.Executors;

/**
 * Parallel finder test.
 */
public class ParallelMaxFindingTest {


    public static void main(String[] args) throws Exception {

        //Array generation
        int elementsNum = 1_000;
        int bound = 3_000_000;
        int[] ints = generateArray(elementsNum, bound);
        System.out.println("Array size: " + ints.length + " elements.");

        //n-thread test
        int procNumber = Runtime.getRuntime().availableProcessors();
        System.out.println("*** " + procNumber + "-threads test ***");
        ParallelHelper helper = new ParallelHelper(Executors.newCachedThreadPool());

        long start = System.nanoTime();
        int result = helper.processArray(ints, procNumber);
        long end = System.nanoTime();
        System.out.println(procNumber + "-threads result: " + result + ". Finding time: " + ((end - start)) / 1000 + " mcs");

        //1-thread test
        System.out.println("*** 1-thread test ***");
        helper = new ParallelHelper(Executors.newFixedThreadPool(1));
        start = System.nanoTime();
        result = helper.processArray(ints, 1);
        end = System.nanoTime();
        System.out.println("1-thread result: " + result + ". Finding time: " + ((end - start)) / 1000 + " mcs");
    }

    private static int[] generateArray(int elementsNum, int bound) {
        Random random = new Random(System.nanoTime());
        int[] ints = new int[elementsNum];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(bound) + 1;
        }
        return ints;
    }
}
