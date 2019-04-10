package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * "Four Sum" quiz solution from LeetCode.
 */
class FourSum {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            threeSumHelper(i + 1, nums, result, target - nums[i]);
        }
        return result;
    }

    private void threeSumHelper(int startIndex, int[] nums, List<List<Integer>> result, int threeSumTarget) {
        for (int i = startIndex; i < nums.length - 2; i++) {
            if (i > startIndex && nums[i] == nums[i - 1]) {
                continue;
            }
            int start = i + 1;
            int end = nums.length - 1;
            int twoSumTarget = threeSumTarget - nums[i];
            while (start < end) {
                int twoSum = nums[start] + nums[end];
                if (twoSum == twoSumTarget) {
                    result.add(Arrays.asList(nums[startIndex - 1], nums[i], nums[start], nums[end]));
                    start++;
                    while (start < end && nums[start - 1] == nums[start]) {
                        start++;
                    }
                    end--;
                    while (start < end && nums[end] == nums[end + 1]) {
                        end--;
                    }
                } else if (twoSum < twoSumTarget) {
                    start++;
                } else {
                    end--;
                }
            }
        }
    }

    @Test
    void test0() {
        int[] input = {1, 0, -1, 0, -2, 2};
        int target = 0;
        List<List<Integer>> result = fourSum(input, target);
        assertTrue(result.contains(Arrays.asList(-2, -1, 1, 2)));
        assertTrue(result.contains(Arrays.asList(-2, 0, 0, 2)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 0, 1)));
    }

    @Test
    void test1() {
        int[] input = {0, 0, 0, 0};
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 0, 0, 0));
        assertEquals(expected, fourSum(input, 0));
    }

    @Test
    void test2() {
        int[] input = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target = 0;
        List<List<Integer>> result = fourSum(input, target);
        assertAll(() -> assertTrue(result.contains(Arrays.asList(-3, -2, 2, 3))),
                () -> assertTrue(result.contains(Arrays.asList(-3, -1, 1, 3))),
                () -> assertTrue(result.contains(Arrays.asList(-3, 0, 0, 3))),
                () -> assertTrue(result.contains(Arrays.asList(-3, 0, 1, 2))),
                () -> assertTrue(result.contains(Arrays.asList(-2, -1, 0, 3))),
                () -> assertTrue(result.contains(Arrays.asList(-2, -1, 1, 2))),
                () -> assertTrue(result.contains(Arrays.asList(-2, 0, 0, 2))),
                () -> assertTrue(result.contains(Arrays.asList(-1, 0, 0, 1))));
        assertEquals(8, result.size());
    }
}
