package org.art.java_core.algorithms.data_structures.utils;

import java.util.Arrays;

import static org.art.java_core.algorithms.data_structures.utils.HeapArrayUtils.buildMaxHeap;

/**
 * Util methods for 'Priority Queue' data structure.
 * Bases on 'Heap array' data structure (from T. Cormen).
 */
public class PriorityQueueUtils {

    public static int heapMaximum(int[] heapArray) {
        return heapArray[0];
    }

    public static int[] insertElement(int[] heapArray, int key) {
        heapArray = trimToHeapSize(heapArray, heapArray.length + 1);
        heapArray[heapArray.length - 1] = key;
        heapIncreaseKey(heapArray, heapArray.length, key);
        return heapArray;
    }

    public static int heapExtractMax(int[] heapArray) {
        int max = heapArray[0];
        heapArray[0] = heapArray[heapArray.length - 1];
        int newHeapSize = heapArray.length - 1;
        HeapArrayUtils.maxHeapify(heapArray, 1, newHeapSize);
        return max;
    }

    public static int[] trimToHeapSize(int[] heapArray, int heapSize) {
        int[] modifiedHeap = new int[heapSize];
        if (heapArray.length >= heapSize) {
            //removing elements from array
            System.arraycopy(heapArray, 0, modifiedHeap, 0, heapSize);
        } else {
            //adding elements from array
            System.arraycopy(heapArray, 0, modifiedHeap, 0, heapArray.length);
        }
        return modifiedHeap;
    }

    public static void heapIncreaseKey(int[] heapArray, int index, int key) {
        if (key < heapArray[index - 1]) {
            throw new IllegalArgumentException("New key is smaller than current key!");
        }
        heapArray[index - 1] = key;
        while (index > 1 && heapArray[index - 1] > heapArray[HeapArrayUtils.parentLeaf(index) - 1]) {
            int tmp = heapArray[HeapArrayUtils.parentLeaf(index) - 1];
            heapArray[HeapArrayUtils.parentLeaf(index) - 1] = heapArray[index - 1];
            heapArray[index - 1] = tmp;
            index = HeapArrayUtils.parentLeaf(index);
        }
    }

    public static void main(String[] args) {

        //Max element extraction test
        int[] testArray1 = {16, 1, 14, 10, 8, 7, 9, 3, 2, 53, 4, 1, 54};
        System.out.println(Arrays.toString(testArray1) + " - array before heap building");
        buildMaxHeap(testArray1);
        System.out.println(Arrays.toString(testArray1) + " - array after building" +
                ", array length: " + testArray1.length);
        int maxExtracted = heapExtractMax(testArray1);
        System.out.println("Max element from Priority Queue: " + maxExtracted);
        testArray1 = trimToHeapSize(testArray1, testArray1.length - 1);
        System.out.println(Arrays.toString(testArray1) + " - array after max element extracting" +
                ", array length: " + testArray1.length + '\n');

        //Increase key test
        int[] testArray2 = {16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
        System.out.println(Arrays.toString(testArray2) + " - array before increasing");
        heapIncreaseKey(testArray2, 9, 15);
        System.out.println(Arrays.toString(testArray2) + " - array after increasing\n");

        //Insertion test
        int[] testArray3 = {16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
        System.out.println(Arrays.toString(testArray3) + " - array before insertion");
        testArray3 = insertElement(testArray3, 20);
        System.out.println(Arrays.toString(testArray3) + " - array after insertion");
    }
}

