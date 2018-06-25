package org.art.java_core.calculations;

import java.util.Scanner;

/**
 * Helper class to initialize values.
 */
public class ValueInitializer {

    private long longValue;
    private double doubleValue;

    private Scanner scanner = new Scanner(System.in);

    boolean initValue() {
        longValue = 0;
        doubleValue = 0;
        if (scanner.hasNextLong()) {
            longValue = scanner.nextLong();
            return true;
        } else if (scanner.hasNextDouble()) {
            doubleValue = scanner.nextDouble();
            return true;
        } else {
            System.out.println("Incorrect number!");
            scanner.next();
            return false;
        }
    }

    long getLongValue() {
        return this.longValue;
    }

    double getDoubleValue() {
        return this.doubleValue;
    }
}
