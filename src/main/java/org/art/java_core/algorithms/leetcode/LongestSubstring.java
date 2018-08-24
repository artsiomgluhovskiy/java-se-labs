package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * "Longest Substring Without Repeating Characters" quiz solution from LeetCode.
 *
 * "To pointers" technique has been implemented.
 */
public class LongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        int i = 0;
        int j = 0;
        int maxLength = 0;
        Set<Character> chars = new HashSet<>();
        while (j < s.length()) {
            if (!chars.contains(s.charAt(j))) {
                chars.add(s.charAt(j));
                j++;
                maxLength = Math.max(maxLength, j - i);
            } else {
                chars.remove(s.charAt(i));
                i++;
            }
        }
        return maxLength;
    }

    @Test
    void test1() {
        String input = "abcabcbb";
        LongestSubstring solution = new LongestSubstring();
        assertSame(3, solution.lengthOfLongestSubstring(input));
    }

    @Test
    void test2() {
        String input = "bbbbb";
        LongestSubstring solution = new LongestSubstring();
        assertSame(1, solution.lengthOfLongestSubstring(input));
    }

    @Test
    void test3() {
        String input = "pwwkew";
        LongestSubstring solution = new LongestSubstring();
        assertSame(3, solution.lengthOfLongestSubstring(input));
    }

    @Test
    void test4() {
        String input = " ";
        LongestSubstring solution = new LongestSubstring();
        assertSame(1, solution.lengthOfLongestSubstring(input));
    }

    @Test
    void test5() {
        String input = "dvdf";
        LongestSubstring solution = new LongestSubstring();
        assertSame(3, solution.lengthOfLongestSubstring(input));
    }

    @Test
    void test6() {
        String input = "pwwk";
        LongestSubstring solution = new LongestSubstring();
        assertSame(2, solution.lengthOfLongestSubstring(input));
    }
}
