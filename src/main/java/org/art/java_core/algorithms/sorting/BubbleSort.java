package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Bubble sort algorithm implementation (from T. Cormen).
 */
public class BubbleSort {

    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] testArray = {3, 6, 1, 7, 2, 9};
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        bubbleSort(testArray);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}
