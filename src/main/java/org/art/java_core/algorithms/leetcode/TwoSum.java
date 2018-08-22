package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * "Two Sum" quiz solution from LeetCode.
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> additions = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int currentValue = nums[i];
            if (additions.containsKey(currentValue)) {
                return new int[]{additions.get(currentValue), i};
            }
            additions.put(target - currentValue, i);
        }
        return new int[0];
    }

    @Test
    void test1() {
        int[] initArray = {2, 7, 11, 15};
        TwoSum twoSum = new TwoSum();
        assertArrayEquals(new int[]{0, 1}, twoSum.twoSum(initArray, 9));
    }

    @Test
    void test2() {
        int[] initArray = {2, 7, 11, 15};
        TwoSum twoSum = new TwoSum();
        assertArrayEquals(new int[]{1, 3}, twoSum.twoSum(initArray, 22));
    }
}
