package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * "Implement strStr()" quiz solution from LeetCode.
 */
public class ImplementStrStr {

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.isEmpty()) {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }
        int hIndex = 0;
        while (hIndex <= haystack.length() - needle.length()) {
            for (int i = 0; i < needle.length(); i++) {
                if (haystack.charAt(hIndex + i) != needle.charAt(i)) {
                    hIndex++;
                    break;
                }
                if (i == needle.length() - 1) {
                    return hIndex;
                }
            }
        }
        return -1;
    }

    @Test
    void test0() {
        String haystack = "hello";
        String needle = "ll";
        int result = strStr(haystack, needle);
        int expected = 2;
        assertSame(expected, result);
    }

    @Test
    void test1() {
        String haystack = "aaa";
        String needle = "bba";
        int result = strStr(haystack, needle);
        int expected = -1;
        assertSame(expected, result);
    }

    @Test
    void test2() {
        String haystack = "ccc";
        String needle = "";
        int result = strStr(haystack, needle);
        int expected = 0;
        assertSame(expected, result);
    }
}
