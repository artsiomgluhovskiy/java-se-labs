package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Container With Most Water" quiz solution from LeetCode.
 *
 * "To pointers" technique has been implemented.
 */
public class MostWater {

    public int maxArea(int[] height) {
        int maxArea = 0;
        if (height == null) {
            return maxArea;
        }
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int val1 = height[left];
            int val2 = height[right];
            int area = (right - left) * Math.min(val1, val2);
            if (area > maxArea) {
                maxArea = area;
            }
            if (val1 > val2) {
                right--;
            } else {
                left++;
            }
        }
        return maxArea;
    }

    @Test
    void test0() {
        int[] input = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        assertEquals(49, maxArea(input));
    }

    @Test
    void test1() {
        int[] input = {3, 7};
        assertEquals(3, maxArea(input));
    }

    @Test
    void test2() {
        int[] input = {3, 3, 7};
        assertEquals(6, maxArea(input));
    }

    @Test
    void test3() {
        assertEquals(0, maxArea(null));
    }
}
