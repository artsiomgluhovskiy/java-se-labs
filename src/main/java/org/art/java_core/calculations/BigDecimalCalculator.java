package org.art.java_core.calculations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * Simple Calculator implementation (with precise and imprecise
 * arithmetic operations).
 */
public class BigDecimalCalculator implements Runnable {

    private static final Double ZERO = 0.0;
    private static final int OPTION_RESET = -2;

    private int precision;
    private int option;
    private long longValue1;
    private long longValue2;
    private double doubleValue1;
    private double doubleValue2;
    private boolean exitFlag;
    private Number value1;
    private Number value2;
    private BigDecimal bigDecimal1;
    private BigDecimal bigDecimal2;
    private Scanner scanner = new Scanner(System.in);

    public BigDecimalCalculator() {}

    private void initValues() {
        ValueInitializer valueInitializer = new ValueInitializer();
        boolean valueInitialized;
        //First number initialization
        System.out.println("Enter your first number (int ot double):");
        do {
            valueInitialized = valueInitializer.initValue();
        } while (!valueInitialized);
        if (valueInitializer.getDoubleValue() != 0) {
            value1 = valueInitializer.getDoubleValue();
        } else {
            value1 = valueInitializer.getLongValue();
        }
        //Second number initialization
        System.out.println("Enter your second number (int or double):");
        do {
            valueInitialized = valueInitializer.initValue();
        } while (!valueInitialized);
        if (valueInitializer.getDoubleValue() != 0) {
            value2 = valueInitializer.getDoubleValue();
        } else {
            value2 = valueInitializer.getLongValue();
        }
        parseValues();
    }

    private void parseValues() {
        if (value1 instanceof Long) {
            longValue1 = value1.longValue();
            bigDecimal1 = BigDecimal.valueOf(longValue1);
        } else {
            doubleValue1 = value1.doubleValue();
            bigDecimal1 = BigDecimal.valueOf(doubleValue1);
        }
        if (value2 instanceof Long) {
            longValue2 = value2.longValue();
            bigDecimal2 = BigDecimal.valueOf(longValue2);
        } else {
            doubleValue2 = value2.doubleValue();
            bigDecimal2 = BigDecimal.valueOf(doubleValue2);
        }
    }

    private void reset() {
        value1 = ZERO;
        value2 = ZERO;
        option = OPTION_RESET;
    }

    private void processOperation() {
        switch (option) {
            case 1:
                if (!isPrecise()) {
                    impreciseAdding();
                } else {
                    preciseAdding();
                }
                break;
            case 2:
                if (!isPrecise()) {
                    impreciseSubtracting();
                } else {
                    preciseSubtracting();
                }
                break;
            case 3:
                if (!isPrecise()) {
                    impreciseMultiplying();
                } else {
                    preciseMultiplying();
                }
                break;
            case 4:
                if (!isPrecise()) {
                    impreciseDividing();
                } else {
                    preciseDividing();
                }
                break;
        }
        System.out.println("Do you want to exit (1 - yes/0 - no)?");
        if (scanner.nextInt() == 1) {
            exitFlag = true;
        }
    }

    private void initPrecision() {
        System.out.println("Choose the precision (number of digits after the decimal point) " +
                "you want (enter \"-1\" to choose imprecise calculations):");
        this.precision = scanner.nextInt();
    }

    private void impreciseAdding() {
        System.out.println("Imprecise calculations: ");
        double sum = (doubleValue1 != 0 ? doubleValue1 : longValue1)
                + (doubleValue2 != 0 ? doubleValue2 : longValue2);
        System.out.println((doubleValue1 != 0 ? doubleValue1 : longValue1) + " + "
                + (doubleValue2 != 0 ? doubleValue2 : longValue2) + " = " + sum + "\n");
    }

    private void impreciseSubtracting() {
        System.out.println("Imprecise calculations: ");
        double sub = (doubleValue1 != 0 ? doubleValue1 : longValue1)
                - (doubleValue2 != 0 ? doubleValue2 : longValue2);
        System.out.println((doubleValue1 != 0 ? doubleValue1 : longValue1) + " - "
                + (doubleValue2 != 0 ? doubleValue2 : longValue2) + " = " + sub + "\n");
    }

    private void impreciseMultiplying() {
        System.out.println("Imprecise calculations: ");
        double mul = (doubleValue1 != 0 ? doubleValue1 : longValue1)
                * (doubleValue2 != 0 ? doubleValue2 : longValue2);
        System.out.println((doubleValue1 != 0 ? doubleValue1 : longValue1) + " * "
                + (doubleValue2 != 0 ? doubleValue2 : longValue2) + " = " + mul + "\n");
    }

    private void impreciseDividing() {
        if ((doubleValue2 != 0 ? doubleValue2 : longValue2) == 0) {
            System.out.println("Error. Division by zero!");
            return;
        }
        System.out.println("Imprecise calculations: ");
        double div = (doubleValue1 != 0 ? doubleValue1 : longValue1)
                / (doubleValue2 != 0 ? doubleValue2 : longValue2);
        System.out.println((doubleValue1 != 0 ? doubleValue1 : longValue1) + " / "
                + (doubleValue2 != 0 ? doubleValue2 : longValue2) + " = " + div + "\n");
    }

    private void preciseAdding() {
        System.out.println("Precise calculations: ");
        BigDecimal sumBig = bigDecimal1.add(bigDecimal2);
        System.out.println("Result (adding): " + sumBig.setScale(precision, BigDecimal.ROUND_CEILING) + "\n");
    }

    private void preciseSubtracting() {
        System.out.println("Precise calculations: ");
        BigDecimal subBig = bigDecimal1.subtract(bigDecimal2);
        System.out.println("Result (subtracting): " + subBig.setScale(precision, BigDecimal.ROUND_CEILING) + "\n");
    }

    private void preciseMultiplying() {
        System.out.println("Precise calculations: ");
        BigDecimal mulBig = bigDecimal1.multiply(bigDecimal2, new MathContext(precision, RoundingMode.CEILING));
        System.out.println("Result (multiplying): " + mulBig.setScale(precision, BigDecimal.ROUND_CEILING) + "\n");
    }

    private void preciseDividing() {
        if (bigDecimal2.doubleValue() == 0) {
            System.out.println("Error. Division by zero!");
            return;
        }
        System.out.println("Precise calculations: ");
        BigDecimal divBig = bigDecimal1.divide(bigDecimal2, precision, BigDecimal.ROUND_CEILING);
        System.out.println("Result (dividing): " + divBig + "\n");
    }

    private int getPrecision() {
        return this.precision;
    }

    private boolean isPrecise() {
        if (this.precision <= -1) {
            return false;
        }
        return true;
    }

    private void initOption() {
        System.out.println("Choose the arithmetic operation:");
        System.out.println("1 - add the numbers;");
        System.out.println("2 - subtract the numbers;");
        System.out.println("3 - multiply the numbers;");
        System.out.println("4 - divide the numbers;");
        int option;
        do {
            option = scanner.nextInt();
        } while (option <= 0 && option > 4);
        this.option = option;
    }

    @Override
    public void run() {
        exitFlag = false;
        while (!exitFlag) {
            reset();
            initValues();
            initPrecision();
            initOption();
            processOperation();
        }
    }
}
