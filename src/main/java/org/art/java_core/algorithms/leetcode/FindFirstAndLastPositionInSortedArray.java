package org.art.java_core.algorithms.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * "Find First and Last Position of Element in Sorted Array" quiz solution from LeetCode.
 */
public class FindFirstAndLastPositionInSortedArray {

    public int[] searchRange(int[] nums, int target) {
        int[] notFoundResult = {-1, -1};
        if (nums.length == 0) return notFoundResult;

        int leftmost = binSearchHelper(nums, target, true);
        if (leftmost == nums.length || nums[leftmost] != target) {
            return notFoundResult;
        }

        int rightmost = binSearchHelper(nums, target, false) - 1;
        return new int[]{leftmost, rightmost};
    }

    private int binSearchHelper(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > target || (left && nums[mid] == target)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    @Test
    void test1() {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        int[] expected = {3, 4};
        Assert.assertArrayEquals(expected, searchRange(nums, target));
    }

    @Test
    void test2() {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 6;
        int[] expected = {-1, -1};
        Assert.assertArrayEquals(expected, searchRange(nums, target));
    }

    @Test
    void test3() {
        int[] nums = {7, 7, 7, 8, 8, 10};
        int target = 7;
        int[] expected = {0, 2};
        Assert.assertArrayEquals(expected, searchRange(nums, target));
    }

    @Test
    void test4() {
        int[] nums = {1};
        int target = 1;
        int[] expected = {0, 0};
        Assert.assertArrayEquals(expected, searchRange(nums, target));
    }

    @Test
    void test5() {
        int[] nums = {2, 2};
        int target = 2;
        int[] expected = {0, 1};
        Assert.assertArrayEquals(expected, searchRange(nums, target));
    }
}
