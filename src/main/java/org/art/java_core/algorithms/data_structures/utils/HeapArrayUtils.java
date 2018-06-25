package org.art.java_core.algorithms.data_structures.utils;

import java.util.Arrays;

/**
 * Util methods for 'Heap array' data structure (from T. Cormen).
 */
public class HeapArrayUtils {

    /**
     * Builds heap array data structure from arbitrary array
     */
    public static void buildMaxHeap(int[] array) {
        int heapSize = array.length;
        for (int i = heapSize / 2; i > 0; i--) {
            maxHeapify(array, i, heapSize);
        }
    }

    /**
     * Maintains the value in a max-heap data structure
     * @param index - the index of leaf to maintain (from 1 to n)
     */
    public static void maxHeapify(int[] heapArray, int index, int heapSize) {
        int leftIndex = leftLeaf(index);
        int rightIndex = rightLeaf(index);
        if (leftIndex > heapSize && rightIndex > heapSize) {
            return;
        }
        int largestValueIndex = index;
        if (leftIndex <= heapSize && heapArray[leftIndex - 1] > heapArray[largestValueIndex - 1]) {
            largestValueIndex = leftIndex;
        }
        if (rightIndex <= heapSize && heapArray[rightIndex - 1] > heapArray[largestValueIndex - 1]) {
            largestValueIndex = rightIndex;
        }
        if (largestValueIndex != index) {
            int tmp = heapArray[largestValueIndex - 1];
            heapArray[largestValueIndex - 1] = heapArray[index - 1];
            heapArray[index - 1] = tmp;
            maxHeapify(heapArray, largestValueIndex, heapSize);
        }
    }

    /**
     * Returns the index of left leaf
     * @param currentIndex - current leaf index
     */
    public static int leftLeaf(int currentIndex) {
        return currentIndex << 1;
    }

    /**
     * Returns the index of right leaf
     * @param currentIndex - current leaf index
     */
    public static int rightLeaf(int currentIndex) {
        return (currentIndex << 1) + 1;
    }

    /**
     * Returns the index of the parent leaf
     * @param currentIndex - current leaf index
     */
    public static int parentLeaf(int currentIndex) {
        if (currentIndex <= 1) {
            throw new IllegalArgumentException("Incorrect index!");
        }
        return currentIndex >> 1;
    }

    public static void main(String[] args) {

        //MaxHeapify test
        int[] testHeapArray = {16, 4, 10, 14, 7, 9, 3, 2, 8, 1};
        System.out.println(Arrays.toString(testHeapArray) + " - original array");
        maxHeapify(testHeapArray, 2, testHeapArray.length);
        System.out.println(Arrays.toString(testHeapArray) + " - after maxHeapify\n");

        //Build Heap Array test
        int[] arbitraryArray = {16, 1, 14, 10, 8, 7, 9, 3, 2, 53, 4, 1, 54};
        System.out.println(Arrays.toString(arbitraryArray) + " - array before heap building");
        buildMaxHeap(arbitraryArray);
        System.out.println(Arrays.toString(arbitraryArray) + " - array after building");
    }
}
