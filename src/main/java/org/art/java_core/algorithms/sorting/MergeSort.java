package org.art.java_core.algorithms.sorting;

import java.util.Arrays;

/**
 * Merge sort algorithm implementation (from T. Cormen).
 */
public class MergeSort {

    public static void mergeSort(int[] array, int lo, int hi) {
        if (lo < hi) {
            int mid = (hi + lo) / 2;
            mergeSort(array, lo , mid);
            mergeSort(array, mid + 1, hi);
            merge(array, lo, mid, hi);
        }
    }

    public static void merge(int[] array, int lo, int mid, int hi) {
        //The size of left array part
        int n1 = mid - lo + 1;
        //The size of right array part
        int n2 = hi - mid;
        int[] left = new int[n1 + 1];
        int[] right = new int[n2 + 1];
        for (int i = 0; i < n1; i++) {
            left[i] = array[lo + i];
        }
        for (int i = 0; i < n2; i++) {
            right[i] = array[i + mid + 1];
        }
        left[n1] = Integer.MAX_VALUE;
        right[n2] = Integer.MAX_VALUE;
        int i = 0;
        int j = 0;
        for (int k = lo; k <= hi; k++) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int[] testArray = {3, 6, 1, 7, 2, 9};
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        mergeSort(testArray, 0, testArray.length - 1);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}
