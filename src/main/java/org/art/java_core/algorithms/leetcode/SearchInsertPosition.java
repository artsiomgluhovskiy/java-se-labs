package org.art.java_core.algorithms.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * "Search Insert Position" quiz solution from LeetCode.
 */
public class SearchInsertPosition {

    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums[0] >= target) {
            return 0;
        }
        return binSearchWithInsertPosition(nums, target, 0, nums.length - 1);
    }

    private int binSearchWithInsertPosition(int[] nums, int target, int lo, int hi) {
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                return binSearchWithInsertPosition(nums, target, mid + 1, hi);
            } else {
                return binSearchWithInsertPosition(nums, target, lo, mid - 1);
            }
        }
        return target > nums[hi] ? hi + 1 : hi;
    }

    @Test
    void test1() {
        int[] input = {1, 3, 5, 6};
        int target = 5;
        int expected = 2;
        Assert.assertEquals(expected, searchInsert(input, target));
    }

    @Test
    void test2() {
        int[] input = {1, 3, 5, 6};
        int target = 2;
        int expected = 1;
        Assert.assertEquals(expected, searchInsert(input, target));
    }

    @Test
    void test3() {
        int[] input = {1, 3, 5, 6};
        int target = 7;
        int expected = 4;
        Assert.assertEquals(expected, searchInsert(input, target));
    }

    @Test
    void test4() {
        int[] input = {1, 3, 5, 6};
        int target = 0;
        int expected = 0;
        Assert.assertEquals(expected, searchInsert(input, target));
    }

    @Test
    void test5() {
        int[] input = {1};
        int target = 1;
        int expected = 0;
        Assert.assertEquals(expected, searchInsert(input, target));
    }

    @Test
    void test6() {
        int[] input = {1, 3};
        int target = 2;
        int expected = 1;
        Assert.assertEquals(expected, searchInsert(input, target));
    }
}
