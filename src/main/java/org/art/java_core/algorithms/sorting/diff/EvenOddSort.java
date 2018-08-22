package org.art.java_core.algorithms.sorting.diff;

import java.util.Arrays;

/**
 * Sorting algorithm implementation.
 * Sorts even numbers in ascending order
 * and odd numbers in descending order.
 * Bases on the selection sort algorithm.
 */
public class EvenOddSort {

    public static void evenOddSort(int[] array) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;
        int minEvenInd = 0;
        int minOddInd = array.length - 1;
        int leftBound = 0;
        int rightBound = array.length - 1;
        while (rightBound - leftBound > 1) {
            for (int i = leftBound; i <= rightBound; i++) {
                int current = array[i];
                if (current % 2 == 0 && current < minEven) {
                    minEven = current;
                    minEvenInd = i;
                }
                if (current % 2 != 0 && current < minOdd) {
                    minOdd = current;
                    minOddInd = i;
                }
            }
            if (minOddInd == rightBound && minEvenInd == leftBound) {
                swapVals(minEvenInd, leftBound, array);
            } else {
                swapVals(minEvenInd, leftBound, array);
                swapVals(minOddInd, rightBound, array);
            }
            minOdd = Integer.MAX_VALUE;
            minEven = Integer.MAX_VALUE;
            leftBound++;
            rightBound--;
        }
    }

    private static void swapVals(int ind1, int ind2, int[] array) {
        int temp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = temp;
    }

    public static void main(String[] args) {
        int[] array = {-1, 3, 4, 1, 9, 0};
        System.out.println(Arrays.toString(array) + " - before sorting");
        evenOddSort(array);
        System.out.println(Arrays.toString(array) + " - after sorting");
    }
}
