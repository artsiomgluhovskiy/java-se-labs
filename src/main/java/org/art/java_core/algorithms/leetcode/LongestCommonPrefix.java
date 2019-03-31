package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Longest Common Prefix" quiz solution from LeetCode.
 */
public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String pref = strs[0];
        for (int i = 1; i < strs.length; i++) {
            String str = strs[i];
            int j = 0;
            while (j < str.length() && j < pref.length() && str.charAt(j) == pref.charAt(j)) {
                j++;
            }
            if (j == 0) {
                return "";
            }
            pref = pref.substring(0, j);
        }
        return pref;
    }

    @Test
    void test0() {
        String[] input = {"flower", "flow", "flight"};
        assertEquals("fl", longestCommonPrefix(input));
    }

    @Test
    void test1() {
        String[] input = {"dog", "racecar", "car"};
        assertEquals("", longestCommonPrefix(input));
    }

    @Test
    void test2() {
        String[] input = {"aa", "a"};
        assertEquals("a", longestCommonPrefix(input));
    }
}
