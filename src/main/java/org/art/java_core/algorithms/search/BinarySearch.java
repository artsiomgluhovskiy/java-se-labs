package org.art.java_core.algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Binary Search algorithm implementation
 */
public class BinarySearch {

    public static int binSearch(int[] nums, int target) {
        return binSearch(nums, target, 0, nums.length - 1);
    }

    public static int binSearch(int[] nums, int target, int i, int j) {
        if (j >= i) {
            int mid = (i + j) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (target < nums[mid]) {
                return binSearch(nums, target, i, mid - 1);
            } else {
                return binSearch(nums, target, mid + 1, j);
            }
        }
        return -1;
    }

    @Test
    void testBinSearch0() {
        int[] nums = {0, 1, 2, 3, 4, 6, 10, 15};
        int target = 6;
        int expected = 5;
        assertEquals(expected, binSearch(nums, target));
    }

    @Test
    void testBinSearch1() {
        int[] nums = {0, 1, 2, 3, 4, 6, 10, 15};
        int target = 5;
        int expected = -1;
        assertEquals(expected, binSearch(nums, target));
    }

    @Test
    void testBinSearch2() {
        int[] nums = {3};
        int target = 3;
        int expected = 0;
        assertEquals(expected, binSearch(nums, target));
    }

    @Test
    void testBinSearch3() {
        int[] nums = {3, 4};
        int target = 5;
        int expected = -1;
        assertEquals(expected, binSearch(nums, target));
    }
}
