package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Longest Palindromic Substring" quiz solution from LeetCode.
 *
 * Dynamic programming approach has been implemented.
 */
public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int right = 0;
        int left = 0;
        int length = s.length();
        boolean[][] isPalindromeData = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            isPalindromeData[i][i] = true;
        }
        for (int j = 1; j < length; j++) {
            for (int i = 0; i < j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPalindromeData[i + 1][j - 1])) {
                    isPalindromeData[i][j] = true;
                    if (j - i > right - left) {
                        left = i;
                        right = j;
                    }
                }
            }
        }
        return s.substring(left, right + 1);
    }

    @Test
    void test0() {
        String input = "babad";
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        assertEquals("bab", solution.longestPalindrome(input));
    }

    @Test
    void test1() {
        String input = "cbbd";
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        assertEquals("bb", solution.longestPalindrome(input));
    }

    @Test
    void test2() {
        String input = "abcda";
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        assertEquals("a", solution.longestPalindrome(input));
    }
}
