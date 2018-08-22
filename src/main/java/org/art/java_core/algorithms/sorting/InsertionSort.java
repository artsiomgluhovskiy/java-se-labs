package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Selection sort algorithm implementation (from T. Cormen).
 */
public class InsertionSort {

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] testArray = {2, 1, 5, 19, 6};
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        insertionSort(testArray);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}
