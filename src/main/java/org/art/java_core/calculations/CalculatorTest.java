package org.art.java_core.calculations;

/**
 * "Big decimal" calculator test.
 */
public class CalculatorTest {

    public static void main(String[] args) {

        //Calculator launching
        new Thread(new BigDecimalCalculator()).start();
    }
}
