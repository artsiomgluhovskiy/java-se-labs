package org.art.java_core.concurrent.scheduling;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Simple scheduler implementation with the ability
 * to define the delay time before starting a job.
 */
public class ScheduledExecutionTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String factoryName = "Custom-thread-factory";
        CustomThreadFactory factory = new CustomThreadFactory(factoryName);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(factory);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Enter the delay time for the job:");
            int time = scanner.nextInt();
            if (time != -1) {
                executor.schedule(new Job(time, "Custom job"), time, TimeUnit.SECONDS);
            } else {
                isRunning = false;
                System.out.println("Process termination!");
            }
        }
        executor.shutdown();
    }
}
