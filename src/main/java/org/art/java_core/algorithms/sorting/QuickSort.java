package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Quick Sort algorithm implementation (with different
 * partition strategies and stack depth measurement)
 * (from T. Cormen).
 */
public class QuickSort {

    public static void quickSort(int[] array, int lo, int hi, PartitionStrategy partitionStrategy,
                                 StackDepthMeasurer measurer) {
        measurer.increaseCounter();
        if (lo < hi) {
            int pivot = partitionStrategy.partition(array, lo, hi);
            quickSort(array, lo, pivot - 1, partitionStrategy, measurer);
            quickSort(array, pivot + 1 , hi, partitionStrategy, measurer);
        }
        measurer.decreaseCounter();
    }

    public static void tailRecursiveQuickSort(int[] array, int lo, int hi, PartitionStrategy partitionStrategy,
                                              StackDepthMeasurer measurer) {
        measurer.increaseCounter();
        while (lo < hi) {
            int pivot = partitionStrategy.partition(array, lo, hi);
            tailRecursiveQuickSort(array, lo, pivot - 1, partitionStrategy, measurer);
            lo = pivot + 1;
        }
        measurer.decreaseCounter();
    }

    public static void main(String[] args) {
        //Partition test
        int[] testArray = {2, 8, 7, 1, 3, 5, 6, 4};
        System.out.println(Arrays.toString(testArray) + " - array before partitioning");
        OriginalPartition partitionStrategy = new OriginalPartition();
        partitionStrategy.partition(testArray, 0, testArray.length - 1);
        System.out.println(Arrays.toString(testArray) + " - array after partitioning");

        //Quick Sort test
        StackDepthMeasurer measurer = new StackDepthMeasurer();
            //Original partition strategy
        int[] testArray2 = {2, 56, 3, 1, 6, 28};
        System.out.println(Arrays.toString(testArray2) + " - array before sorting");
        OriginalPartition origPartition = new OriginalPartition();
        quickSort(testArray2, 0, testArray2.length - 1, origPartition, measurer);
        System.out.println(Arrays.toString(testArray2) + " - array after sorting (origin strategy)");
        System.out.println("Stack depth (with double recursion calls): " + measurer.getMaxDepth());
        measurer.resetMeasurer();
            //Hoare partition strategy
        HoarePartiotion hoarePartition = new HoarePartiotion();
        int[] testArray3 = {2, 56, 3, 1, 6, 28};
        System.out.println(Arrays.toString(testArray3) + " - array before sorting");
        tailRecursiveQuickSort(testArray3, 0, testArray3.length - 1, hoarePartition, measurer);
        System.out.println(Arrays.toString(testArray3) + " - array after sorting (Hoare strategy)");
        System.out.println("Stack depth (tail recursion call): " + measurer.getMaxDepth());
    }
}

/**
 * Strategy Pattern Implementation (from GoF)
 */
interface PartitionStrategy {
    int partition(int[] array, int lo, int hi);
}

class OriginalPartition implements PartitionStrategy {

    @Override
    public int partition(int[] array, int lo, int hi) {
        int pivotIndex = hi;
        int pivot = array[pivotIndex];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= pivot) {
                i = i + 1;
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        int tmp = array[i + 1];
        array[i + 1] = pivot;
        array[pivotIndex] = tmp;
        return i + 1;
    }
}

class HoarePartiotion implements PartitionStrategy {

    @Override
    public int partition(int[] array, int lo, int hi) {
        int pivot = array[lo];
        int i = lo;
        int j = hi;
        while (true) {
            while (array[i] < pivot) {
                i += 1;
            }
            while (array[j] > pivot) {
                j -= 1;
            }
            if (i < j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            } else {
                return j;
            }
        }
    }
}

class StackDepthMeasurer {

    private int depthCounter = 0;
    private int maxDepth = 0;

    void increaseCounter() {
        depthCounter++;
    }

    void decreaseCounter() {
        setMaxDepth();
        depthCounter--;
    }

    void resetMeasurer() {
        depthCounter = 0;
        maxDepth = 0;
    }

    private void setMaxDepth() {
        if (depthCounter > maxDepth) {
            maxDepth = depthCounter;
        }
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
