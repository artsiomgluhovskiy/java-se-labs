package org.art.java_core.reflection_api.transaction_analyzer;

/**
 * Annotated Test class.
 */
public class AnnotatedTestClass {

    private int value;
    private String str;
    private Integer number;

    public AnnotatedTestClass(String str, int value, Integer number) {
        this.str = str;
        this.value = value;
        this.number = number;
    }

    public AnnotatedTestClass() {}

    @Transaction
    public void annotatedMethod1(int num) {
        System.out.printf("First annotated 'transaction' method of the Class \"%s\". Number %d.%n", getClass().getSimpleName(), num);
    }

    public void regularMethod(int num) {
        System.out.printf("Regular method of the Class \"%s\".", getClass().getSimpleName());
    }

    private int privateMethod() {
        System.out.printf("Private method of the Class \"%s\".%n", getClass().getSimpleName());
        return 1;
    }

    @Transaction
    public void annotatedMethod2(int num) {
        System.out.printf("Second annotated 'transaction' method of the Class \"%s\". Number %d.%n", getClass().getSimpleName(), num);
    }

    public static void main(String[] args) {
        //Transaction analyzer test
        TransactionWrapper.processTransaction(AnnotatedTestClass.class);
    }
}
