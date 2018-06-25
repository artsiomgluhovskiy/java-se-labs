package org.art.java_core.reflection_api.class_analyzer;


import org.art.java_core.reflection_api.transaction_analyzer.Transaction;

/**
 * Test class for 'Class Analyzer'.
 */
public class TestClass extends Thread {

    public int value;

    private String str;

    protected Integer number;

    public TestClass(String str, int value, Integer number) {
        this.str = str;
        this.value = value;
        this.number = number;
    }

    private TestClass(int value) {
        this.value = value;
    }

    private TestClass() {
    }

    @Transaction
    public void annotatedMethod1(int num) {
        System.out.printf("First annotated 'transaction' method of the Class \"%s\". Number %d.", getClass().getSimpleName(), num);
    }

    public void regularMethod(int num) {
        System.out.printf("Regular method of the Class \"%s\".", getClass().getSimpleName());
    }

    private int privateMethod() {
        System.out.printf("Private method of the Class \"%s\".", getClass().getSimpleName());
        return 1;
    }

    @Transaction
    public void annotatedMethod2(int num) {
        System.out.printf("Second annotated 'transaction' method of the Class \"%s\". Number %d.", getClass().getSimpleName(), num);
    }

    public String getStr() {
        return str;
    }

    /**
     * Private inner class.
     */
    class InnerClass {

        private int innerValue;

        public InnerClass(int innerValue) {
            this.innerValue = innerValue;
        }
    }

    /**
     * Nested static class.
     */
    public static class NestedClass {

        private int nestedVal;

        public NestedClass(int nestedVal) {
            this.nestedVal = nestedVal;
        }
    }
}
