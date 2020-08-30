package org.art.java_core.algorithms.leetcode;

import org.art.java_core.algorithms.search.BinarySearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Search In Rotated Sorted Array" quiz solution from LeetCode.
 */
public class SearchInRotatedSortedArray {

    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }
        int i = 0;
        int j = len - 1;
        return searchHelper(nums, target, i, j);
    }

    private int searchHelper(int[] nums, int target, int i, int j) {
        if (i == j) {
            if (nums[i] == target) {
                return i;
            } else {
                return -1;
            }
        }
        int mid = (j + i) / 2;
        if (nums[i] <= nums[mid]) {
            if (nums[i] <= target && target <= nums[mid]) {
                return BinarySearch.binSearch(nums, target, i, mid);
            } else {
                return searchHelper(nums, target, mid + 1, j);
            }
        } else {
            if (nums[mid + 1] <= target && target <= nums[j]) {
                return BinarySearch.binSearch(nums, target, mid + 1, j);
            } else {
                return searchHelper(nums, target, i, mid);
            }
        }
    }

    @Test
    void test0() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int expected = 4;
        assertEquals(expected, search(nums, target));
    }

    @Test
    void test1() {
        int[] nums = {4, 5, 6, 7, 18, 23, 0, 1, 2};
        int target = 5;
        int expected = 1;
        assertEquals(expected, search(nums, target));
    }

    @Test
    void test2() {
        int[] nums = {4, 5, 6, 18, 23, 24, 28, 30};
        int target = 5;
        int expected = 1;
        assertEquals(expected, search(nums, target));
    }

    @Test
    void test3() {
        int[] nums = {4, 5, 6, 18, 23, 24, 28, 30};
        int target = 30;
        int expected = 7;
        assertEquals(expected, search(nums, target));
    }

    @Test
    void test4() {
        int[] nums = {4, 5, 6, 18, 23, 24, 28, 30};
        int target = 31;
        int expected = -1;
        assertEquals(expected, search(nums, target));
    }

    @Test
    void test5() {
        int[] nums = {4, 5};
        int target = 5;
        int expected = 1;
        assertEquals(expected, search(nums, target));
    }
}
