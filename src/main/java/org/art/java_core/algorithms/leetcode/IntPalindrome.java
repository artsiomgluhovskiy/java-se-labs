package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "Integer Palindrome" quiz solution from LeetCode.
 */
public class IntPalindrome {

    public boolean isPalindrome(int x) {
        int initialNum = x;
        int reversed = 0;
        if (x < 0) {
            return false;
        }
        while (x != 0) {
            reversed = reversed * 10 + x % 10;
            x = x / 10;
        }
        return initialNum == reversed;
    }

    @Test
    @DisplayName("121 -> true")
    void test0() {
        assertTrue(isPalindrome(121));
    }

    @Test
    @DisplayName("-121 -> false")
    void test1() {
        assertFalse(isPalindrome(-121));
    }
}
