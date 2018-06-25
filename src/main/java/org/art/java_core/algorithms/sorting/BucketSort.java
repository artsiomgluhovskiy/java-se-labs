package org.art.java_core.algorithms.sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Bucket sort algorithm implementation O(n) (from T. Cormen).
 */
public class BucketSort {

    public static double[] bucketSort(double[] array) {
        Bucket[] buckets = new Bucket[array.length];
        for (int i = 0; i < array.length; i++) {
            //Simple hash function analogy
            int bucketIndex = (int) (array[i] * buckets.length);
            Bucket bucketRef = buckets[bucketIndex];
            buckets[bucketIndex] = insertBucket(bucketRef, array[i]);
        }
        return generateArray(buckets, array.length);
    }

    /**
     * Inserts the bucket node in accordance with the ascending value ordering
     *
     * @param bucket - tail reference to the bucket linked list
     * @param val    - value of the node to insert
     * @return the tail reference to the bucket linked list
     */
    private static Bucket insertBucket(Bucket bucket, double val) {
        if (bucket == null) {
            return new Bucket(val, null);
        } else if (val < bucket.value) {
            return new Bucket(val, bucket);
        }
        Bucket initRef = bucket;
        while (val >= bucket.value && bucket.next != null && val > bucket.next.value) {
            bucket = bucket.next;
        }
        bucket.next = new Bucket(val, bucket.next);
        return initRef;
    }

    /**
     * Generates new sorted array from the array of buckets.
     *
     * @param buckets - bucket array
     * @param size    - the size of sorted array
     * @return the sorted array
     */
    private static double[] generateArray(Bucket[] buckets, int size) {
        double[] sortedArray = new double[size];
        int counter = 0;
        for (int i = 0; i < buckets.length; i++) {
            Bucket ref = buckets[i];
            while (ref != null) {
                sortedArray[counter] = ref.value;
                counter++;
                ref = ref.next;
            }
        }
        return sortedArray;
    }

    /**
     * Fills array with random values from 0 (inclusive) to 1 (exclusive).
     *
     * @param array - array to fill
     * @return the filled array
     */
    private static double[] fillWithRandoms(double[] array) {
        Random rnd = new Random(System.nanoTime());
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (rnd.nextDouble() * 100) / (double) 100;
        }
        return array;
    }

    public static void main(String[] args) {
        double[] testArray = new double[10];
        testArray = fillWithRandoms(testArray);
        System.out.println(Arrays.toString(testArray) + " - before sorting");
        testArray = bucketSort(testArray);
        System.out.println(Arrays.toString(testArray) + " - after sorting");
    }
}

class Bucket {

    double value;
    Bucket next;

    Bucket(double value, Bucket next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Bucket{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
