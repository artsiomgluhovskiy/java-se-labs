package org.art.java_core.algorithms.sorting;

import org.art.java_core.algorithms.data_structures.utils.HeapArrayUtils;

import java.util.Arrays;

/**
 * Heap sort algorithm implementation O(n lgn) (from T. Cormen).
 */
public class HeapSort {

    public static void heapSort(int[] array) {
        int arLength = array.length;
        HeapArrayUtils.buildMaxHeap(array);
        for (int i = arLength - 1; i > 0; i--) {
            int tmp = array[i];
            array[i] = array[0];
            array[0] = tmp;
            int heapSize = i;
            HeapArrayUtils.maxHeapify(array, 1, heapSize);
        }
    }

    public static void main(String[] args) {
        int[] testArray = {2, 1, 64, 8, 22, 10, 3, 5};
        System.out.println("Array before sorting: " + Arrays.toString(testArray));
        heapSort(testArray);
        System.out.println("Array after sorting: " + Arrays.toString(testArray));
    }
}
