package org.art.java_core.algorithms.leetcode;

import org.art.java_core.algorithms.leetcode.utils.ArraysUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "Remove Element" quiz solution from LeetCode.
 */
public class RemoveElementArrayInPlace {

    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 0;
        int j = 1;
        while (j < nums.length) {
            if (nums[i] != val) {
                i++;
            } else {
                if (nums[j] != val) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    i++;
                }
            }
            j++;
        }
        return nums[i] == val ? i : i + 1;
    }

    @Test
    void test1() {
        int[] nums = {3, 2, 2, 3};
        int val = 3;
        int[] beginsWith = {2, 2};
        int result = removeElement(nums, val);
        assertEquals(2, result);
        assertTrue(ArraysUtils.arrayBeginsPrecisely(nums, beginsWith));
    }

    @Test
    void test2() {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int val = 2;
        int[] beginsWith = {0, 1, 3, 0, 4};
        int result = removeElement(nums, val);
        assertEquals(5, result);
        assertTrue(ArraysUtils.arrayBeginsPrecisely(nums, beginsWith));
    }
}
