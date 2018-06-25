package org.art.java_core.concurrent.parallel_processing;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Parallel helper class implementation.
 */
public class ParallelHelper {

    private ExecutorService pool;

    public ParallelHelper() {
        pool = Executors.newCachedThreadPool();
    }

    public ParallelHelper(ExecutorService pool) {
        this.pool = pool;
    }

    public int processArray(int[] ints, int procNumber) throws Exception {
        Deque<Future<Integer>> futures = new LinkedList<>();
        int from = 0;
        int to = ints.length / procNumber;
        for (int i = 0; i < procNumber; i++) {
            if (i == procNumber - 1) {
                to = ints.length;
            }
            ParallelMaxFinder finder = new ParallelMaxFinder(ints, from, to);
            futures.add(pool.submit(finder));
            from = to;
            to = to + ints.length / procNumber;
        }
        pool.shutdown();
        System.out.println("Max number of threads in the pool: " + ((ThreadPoolExecutor) pool).getLargestPoolSize());
        return getResult(futures);
    }

    private int getResult(Deque<Future<Integer>> futures) throws Exception {
        List<Integer> results = new ArrayList<>();
        while (!futures.isEmpty()) {
            Future<Integer> future = futures.pollFirst();
            if (future != null && future.isDone()) {
                results.add(future.get());
            } else {
                //Returning back to the list
                futures.addLast(future);
            }
        }
        return Collections.max(results);
    }
}
