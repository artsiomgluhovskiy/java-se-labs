package org.art.java_core.algorithms.search;

import java.util.Arrays;
import java.util.Random;

/**
 * Finding the i-th order statistic in the array O(n) (from T. Cormen).
 * i-th order statistic - is the element in array that is larger than
 * exactly i-1 other elements of the array.
 */
public class RandomizedSelect {

    public static int randomizedSelect(int[] array, int lo, int hi, int orderStatistic) {
        if (lo == hi) {
            return array[lo];
        }
        int q = randomizedPartition(array, lo, hi);
        int k = q - lo + 1;
        if (k == orderStatistic) {
            return array[q];
        } else if (orderStatistic < k) {
            return randomizedSelect(array, lo, q - 1, orderStatistic);
        } else {
            return randomizedSelect(array, q + 1, hi, orderStatistic - k);
        }
    }

    private static int randomizedPartition(int[] array, int lo, int hi) {
        Random rnd = new Random(System.nanoTime());
        int random = rnd.nextInt(hi - lo + 1) + lo;
        int tmp = array[lo];
        array[lo] = array[random];
        array[random] = tmp;
        return partition(array, lo, hi);
    }

    private static int partition(int[] array, int lo, int hi) {
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

    public static void main(String[] args) {
        int[] testArray = {3, 1, 5, 6, 2, 8, 9};
        System.out.println(Arrays.toString(testArray) + " - test array");
        int stat = 5;
        System.out.println(stat + " order statistic for array is: " +
                randomizedSelect(testArray, 0, testArray.length - 1, stat));
    }
}
