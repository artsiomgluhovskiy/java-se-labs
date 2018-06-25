package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Counting sort algorithm implementation O(n) (from T. Cormen).
 * The algorithm is stable (numbers with the same value appear in
 * the output array in the same order as they do in the input array).
 * In addition, the algorithm is not based on comparison principle.
 */
public class CountingSort {

    /**
     * Sorts the array in according with counting sort algorithm
     * @param array - array to sort
     * @param k - corresponds to the max range value [0...k]
     * @return - sorted array
     */
    public static int[] countingSort(int[] array, int k) {
        int[] sortedArray = new int[array.length];
        int[] auxArray = new int[k + 1];
        Arrays.fill(auxArray, 0);
        for (int i = 0; i < array.length; i++) {
            auxArray[array[i]] = auxArray[array[i]] + 1;
        }
        for (int i = 0; i < auxArray.length - 1; i++) {
            auxArray[i + 1] = auxArray[i + 1] + auxArray[i];
        }
        System.out.println(Arrays.toString(auxArray));
        for (int i = sortedArray.length - 1; i >= 0; i--) {
            sortedArray[auxArray[array[i]] - 1] = array[i];
            auxArray[array[i]] = auxArray[array[i]] - 1;
        }
        return sortedArray;
    }

    public static void main(String[] args) {
        int[] testArray = {2, 1, 3, 5, 1, 5, 2, 2, 4, 0, 1};
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        testArray = countingSort(testArray, 5);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}
