package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Three Sum Closest" quiz solution from LeetCode.
 */
class ThreeSumClosest {

    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        int left;
        int right;
        for (int i = 0; i < nums.length; i++) {
            left = i + 1;
            right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                }
                if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                    closestSum = sum;
                }
                if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return closestSum;
    }

    @Test
    void test0() {
        int[] inputArray = {-1, 2, 1, -4};
        int target = 1;
        assertEquals(2, threeSumClosest(inputArray, target));
    }

    @Test
    void test1() {
        int[] inputArray = {-1, 2, 1, -4};
        int target = -4;
        assertEquals(-4, threeSumClosest(inputArray, target));
    }

    @Test
    void test2() {
        int[] inputArray = {1, 2, 4, 8, 16, 32, 64, 128};
        int target = 82;
        assertEquals(82, threeSumClosest(inputArray, target));
    }
}
