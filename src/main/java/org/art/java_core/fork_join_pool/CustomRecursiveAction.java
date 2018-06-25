package org.art.java_core.fork_join_pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Parallel string processor (via RecursiveAction API).
 * Transforms initial string into upper case format.
 *
 * Divide and process idiom is implemented (without merging
 * the results of individual subtasks).
 */
public class CustomRecursiveAction extends RecursiveAction {

    private static final int THRESHOLD = 4;

    private String workload;

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }

    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workload);
        }
    }

    private List<CustomRecursiveAction> createSubtasks() {
        List<CustomRecursiveAction> subtasks = new ArrayList<>();

        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2, workload.length());

        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));

        return subtasks;
    }

    private void processing(String work) {
        String result = work.toUpperCase();
        System.out.printf("This result - (%s) - was processed by %s%n", result, Thread.currentThread().getName());
    }
}
