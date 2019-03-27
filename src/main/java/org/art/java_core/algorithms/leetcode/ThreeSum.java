package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "Three Sum" quiz solution from LeetCode.
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int targetVal = -nums[i];
            twoSumHelper(nums, result, targetVal, i + 1, nums.length - 1);
        }
        return result;
    }

    private void twoSumHelper(int[] nums, List<List<Integer>> result, int targetVal, int left, int right) {
        while (left < right) {
            if (nums[left] + nums[right] == targetVal) {
                List<Integer> list = new ArrayList<>();
                list.add(-targetVal);
                list.add(nums[left]);
                list.add(nums[right]);
                result.add(list);
                left++;
                right--;
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }
            } else if (nums[left] + nums[right] > targetVal) {
                right--;
            } else {
                left++;
            }
        }
    }

    @Test
    void test0() {
        int[] input = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> actual = threeSum(input);
        assertEquals(2, actual.size());
        assertTrue(actual.contains(Arrays.asList(-1, 0, 1)));
        assertTrue(actual.contains(Arrays.asList(-1, -1, 2)));
    }
}
