package org.art.java_core.design.patterns.strategy;

/**
 * Strategy pattern (from GoF) - simple code example.
 * Array Sorting strategies (dummy) implementation.
 */
public class StrategyImpl {

    public static void main(String[] args) {

        ArraySorter sorter = new ArraySorter();
        int[] arr = {1, 4, 6, 3};

        sorter.setSortingStrategy(new BubbleSort());
        sorter.sortArray(arr);

        sorter.setSortingStrategy(new SelectionSort());
        sorter.sortArray(arr);

        sorter.setSortingStrategy(new InsertingSort());
        sorter.sortArray(arr);

        sorter.setSortingStrategy(new MergeSort());
        sorter.sortArray(arr);
    }
}

class ArraySorter {

    private SortingStrategyIF strategy;

    void setSortingStrategy(SortingStrategyIF strategy) {
        this.strategy = strategy;
    }
    void sortArray(int[] arr) {
        strategy.sort(arr);
    }
}

interface SortingStrategyIF {
    void sort(int[] arr);
}

/**
 * Bubble sort algorithm dummy implementation
 */
class BubbleSort implements SortingStrategyIF {

    @Override
    public void sort(int[] arr) {
        System.out.println("Bubble sort algorithm...");
    }
}

/**
 * Selection sort algorithm dummy implementation
 */
class SelectionSort implements SortingStrategyIF {

    @Override
    public void sort(int[] arr) {
        System.out.println("Selection sort algorithm...");
    }
}

/**
 * Inserting sort algorithm dummy implementation
 */
class InsertingSort implements SortingStrategyIF {

    @Override
    public void sort(int[] arr) {
        System.out.println("Inserting sort algorithm...");
    }
}

/**
 * Merge sort algorithm dummy implementation
 */
class MergeSort implements SortingStrategyIF {

    @Override
    public void sort(int[] arr) {
        System.out.println("Merge sort algorithm...");
    }
}
