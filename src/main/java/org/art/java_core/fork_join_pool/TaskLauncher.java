package org.art.java_core.fork_join_pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

/**
 * ForkJoinPool API code examples (parallel data processing).
 */
public class TaskLauncher {

    public static void main(String[] args) {

        //CustomRecursiveAction Execution
        String testString = "Hello from Fork/Join Pool Framework";
        CustomRecursiveAction action = new CustomRecursiveAction(testString);
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("Initial string: " + testString);
        commonPool.execute(action);

        //CustomRecursiveTask Execution
        int[] array1 = {2, 4, 2, 1, 6, 32, 11, 64, 2, 6, 9, 10, 6, 97};
        CustomRecursiveTask task = new CustomRecursiveTask(array1);
        int result = commonPool.invoke(task);
        System.out.println("Initial array:\n" + Arrays.toString(array1));
        System.out.println("Max value in array is: " + result);

        //MapReducer Task Execution
        int[] array2 = new int[]{2, 4, 2, 1, 6, 32, 102, 64, 2, 6, 9, 10, 6, 97};
        Function<Integer, Integer> mapper = (val) -> val;                       //Some dummy mapper function
        MapReducer mapReducer = new MapReducer(null, array2, mapper, 0, array2.length);
        int mapReducerResult = mapReducer.invoke();
        System.out.println("Initial array:\n" + Arrays.toString(array2));
        System.out.println("MapReduce result: " + mapReducerResult);
    }
}
