package org.art.java_core.algorithms.search;

import java.util.Arrays;

/**
 * The implementation of the max sum sub array finding algorithm (from T. Cormen).
 */
public class MaxSubArrayFinder {

    static Tuple_3 findMaxSubArray(int[] a, int low, int hi) {
        if (low == hi) {
            return new Tuple_3(low, hi, a[low]);
        }
        int mid = (low + hi) / 2;
        //Left sub array processing
        Tuple_3 left = findMaxSubArray(a, low, mid);
        //Right sub array processing
        Tuple_3 right = findMaxSubArray(a, mid + 1, hi);
        //Cross sub array processing
        Tuple_3 cross = findMaxCrossSubArray(a, mid, low, hi);
        if (left.sum >= right.sum && left.sum >= cross.sum) {
            return new Tuple_3(left.low, left.hi, left.sum);
        } else if (right.sum >= left.sum && right.sum >= cross.sum) {
            return new Tuple_3(right.low, right.hi, right.sum);
        } else {
            return new Tuple_3(cross.low, cross.hi, cross.sum);
        }
    }

    static Tuple_3 findMaxCrossSubArray(int[] a, int mid, int low, int hi) {
        int leftSum = -9999999;
        int sum = 0;
        int crossLow = 0;
        for (int i = mid; i >= low; i--) {
            sum = sum + a[i];
            if (sum >= leftSum) {
                leftSum = sum;
                crossLow = i;
            } else {
                break;
            }
        }
        int rightSum = -9999999;
        sum = 0;
        int crossHi = 0;
        for (int i = mid + 1; i <= hi; i++) {
            sum = sum + a[i];
            if (sum >= rightSum) {
                rightSum = sum;
                crossHi = i;
            } else {
                break;
            }
        }
        return new Tuple_3(crossLow, crossHi, leftSum + rightSum);
    }

    static int[] getArrayFromTuple(int[] array, Tuple_3 arrayData) {
        if (arrayData != null) {
            int newArraySize = arrayData.hi - arrayData.low + 1;
            int[] newArray = new int[newArraySize];
            System.arraycopy(array, arrayData.low, newArray, 0, newArraySize);
            return newArray;
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] array = new int[]{-13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, 22, 15};
        Tuple_3 subArrayData = findMaxSubArray(array, 0, array.length - 1);
        int[] maxSubArray = getArrayFromTuple(array, subArrayData);
        System.out.println("Sub array is: " + Arrays.toString(maxSubArray) + ", sum = " + subArrayData.sum);
    }
}

class Tuple_3 {

    int low;
    int hi;
    int sum;

    Tuple_3(int sideLow, int sideHi, int sideSum) {
        this.low = sideLow;
        this.hi = sideHi;
        this.sum = sideSum;
    }

    @Override
    public String toString() {
        return "Tuple_3{" +
                "low=" + low +
                ", hi=" + hi +
                ", sum=" + sum +
                '}';
    }
}
