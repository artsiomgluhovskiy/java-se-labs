package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * "Next Permutation" quiz solution from LeetCode.
 */
public class NextPermutation {

    public void nextPermutation(int[] nums) {

        if (nums == null || nums.length == 1) {
            return;
        }

        int n = nums.length - 1;
        int i = n - 1;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n;
            while (i < j && nums[i] >= nums[j]) {
                j--;
            }

            swap(nums, i, j);
        }

        reverse(nums, i + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int lo) {
        int hi = nums.length - 1;
        while(lo < hi) {
            swap(nums, lo, hi);
            lo++;
            hi--;
        }
    }

    @Test
    void test1() {
        int[] actual = {1};
        int[] expected = {1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test2() {
        int[] actual = null;
        int[] expected = null;
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test3() {
        int[] actual = {1, 2, 3};
        int[] expected = {1, 3, 2};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test4() {
        int[] actual = {3, 2, 1};
        int[] expected = {1, 2, 3};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test5() {
        int[] actual = {1, 1, 5};
        int[] expected = {1, 5, 1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test6() {
        int[] actual = {1, 1, 1};
        int[] expected = {1, 1, 1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test7() {
        int[] actual = {1, 2, 1};
        int[] expected = {2, 1, 1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test8() {
        int[] actual = {1, 1};
        int[] expected = {1, 1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test9() {
        int[] actual = {1, 1, 5, 1, 2};
        int[] expected = {1, 1, 5, 2, 1};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test10() {
        int[] actual = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test11() {
        int[] actual = {5, 2, 3, 4, 1};
        int[] expected = {5, 2, 4, 1, 3};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test12() {
        int[] actual = {5, 4, 2, 1};
        int[] expected = {1, 2, 4, 5};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void test13() {
        int[] actual = {1, 3, 2};
        int[] expected = {2, 1, 3};
        nextPermutation(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}
