package org.art.java_core.concurrent.java_util_concurrent.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simple data consistency test for thread unsafe {@link HashMap}
 * and {@link java.util.concurrent.ConcurrentHashMap} under
 * concurrent modification.
 */
public class ConcurrentHashMapTest {

    private static final String TARGET_KEY = "target";

    private static final int N_CPU = Runtime.getRuntime().availableProcessors();

    private static void runMapMutationTest(Map<String, Integer> map) throws InterruptedException {
        map.put(TARGET_KEY, 0);
        System.out.println("Number of available processors: " + N_CPU);
        ExecutorService executor = Executors.newFixedThreadPool(N_CPU);
        int mutationsPerThread = 50;
        int totalMutations = mutationsPerThread * N_CPU;
        System.out.println("Total amount of map mutations: " + totalMutations);
        for (int i = 0; i < N_CPU; i++) {
            executor.submit(() -> {
                for (int k = 0; k < mutationsPerThread; k++) {
                    map.computeIfPresent(TARGET_KEY, (key, value) -> value + 1);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        int actualMutations = map.get(TARGET_KEY);
        System.out.println(actualMutations != totalMutations ? "*** Test Failed!" : "**** Test Passed!");
        System.out.println(map.getClass().getSimpleName() + " Result: value after mutations: " + map.get(TARGET_KEY));
    }

    public static void main(String[] args) throws InterruptedException {
        //Running HashMap concurrent test
        runMapMutationTest(new HashMap<>());

        //Running ConcurrentHashMap concurrent test
        runMapMutationTest(new ConcurrentHashMap<>());
    }
}
