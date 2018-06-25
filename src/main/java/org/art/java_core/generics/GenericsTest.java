package org.art.java_core.generics;

import java.util.function.UnaryOperator;

public class GenericsTest {

    private static final UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    private static void genericPairTest() {

        System.out.println("Task 1. Generic pair test");

        Pair<String, Double> pair1 = new Pair<>("Hello", 1.0);
        Pair<Double, String> pair2 = new Pair<>(1.0, "Hello");
        System.out.println(pair1);
        System.out.println(pair2);

        //Swapping
        Pair<Double, String> swappedPair = PairUtils.swap(pair1);
        System.out.println("Swapped pair: " + swappedPair);

        SubPair subPair = new SubPair("Param 1", "Param 2", 3);
        System.out.println(subPair);
        printEmptyLines(1);
    }

    private static void singletonFactoryTest() {

        System.out.println("Task 2. Generic singleton factory pattern (from Effective Java)");

        String[] strings = {"jute", "hemp", "nylon"};

        UnaryOperator<String> sameString = identityFunction();
        for (String string : strings) {
            System.out.println(sameString.apply(string));
        }
        Number[] numbers = {1, 2.0, 3L};
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number number : numbers) {
            System.out.println(sameNumber.apply(number));
        }
        printEmptyLines(1);
    }

    private static void heterogeneousContainerTest() {

        System.out.println("Task 3. Heterogeneous container pattern (from Effective Java");

        HeterogeneousContainer container = new HeterogeneousContainer();
        container.putItem(String.class, "String value");
        container.putItem(Integer.class, 3);
        container.putItem(Thread.class, new Thread());
        System.out.println(container);

        String stringItem = container.getItem(String.class);
        Integer intItem = container.getItem(Integer.class);
        Thread threadItem = container.getItem(Thread.class);

        System.out.println("String item: " + stringItem);
        System.out.println("Integer item: " + intItem);
        System.out.println("Thread item: " + threadItem);
        printEmptyLines(1);
    }

    private static void customStackTest() {

        System.out.println("Task 4. Custom generic stack test");

        Stack<String> stack = new Stack<>();
        stack.push("Item 1");
        stack.push("Item 2");
        stack.push("Item 3");

        while (!stack.isEmpty()) {
            System.out.println(stack.pop().toUpperCase());
        }

        printEmptyLines(1);
    }

    @SuppressWarnings("unchecked")
    private static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //Task 1
        genericPairTest();

        //Task 2
        singletonFactoryTest();

        //Task 3
        heterogeneousContainerTest();

        //Task 4
        customStackTest();
    }
}
