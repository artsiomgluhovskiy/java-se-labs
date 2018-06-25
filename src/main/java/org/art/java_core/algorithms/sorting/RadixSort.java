package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Radix sort algorithm implementation (from T. Cormen).
 * Bases on counting sort algorithm. The algorithm is stable.
 */
public class RadixSort {

    public static final int DIGIT_NUMBER = 3;

    /**
     * Sorts the array in accordance with the radix sort algorithm
     *
     * @param array - array to sort
     * @return the sorted array
     */
    public static int[] radixSort(int[] array) {
        for (int i = 1; i <= DIGIT_NUMBER; i++) {
            array = countingSort(array, 9, i);
        }
        return array;
    }

    /**
     * Sorts the array according to counting sort algorithm
     *
     * @param array - array to sort
     * @param k     - corresponds to the max range value [0...k]
     * @param d     - the digit position to sort
     * @return - sorted array
     */
    private static int[] countingSort(int[] array, int k, int d) {
        int[] sortedArray = new int[array.length];
        int[] auxArray = new int[k + 1];
        Arrays.fill(auxArray, 0);
        for (int i = 0; i < array.length; i++) {
            int newIndex = getDigitByPosition(array[i], d);
            auxArray[newIndex] = auxArray[newIndex] + 1;
        }
        for (int i = 0; i < auxArray.length - 1; i++) {
            auxArray[i + 1] = auxArray[i + 1] + auxArray[i];
        }
        for (int i = sortedArray.length - 1; i >= 0; i--) {
            int newIndex = getDigitByPosition(array[i], d);
            sortedArray[auxArray[newIndex] - 1] = array[i];
            auxArray[newIndex] = auxArray[newIndex] - 1;
        }
        return sortedArray;
    }

    private static int getDigitByPosition(int value, int position) {
        return (value / (int) Math.pow(10, position - 1)) % 10;
    }

    public static void main(String[] args) {
        int[] testArray = {345, 532, 684, 394, 112, 353};
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        testArray = radixSort(testArray);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}
