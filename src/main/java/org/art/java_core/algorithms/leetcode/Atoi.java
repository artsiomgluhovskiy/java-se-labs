package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * "Atoi" quiz solution from LeetCode.
 */
public class Atoi {

    public int myAtoi(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        str = str.trim();
        if (str.isEmpty()) {
            return 0;
        }
        double result = 0;
        int digit;
        int startIndex = 0;
        boolean isNegative = false;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') {
            startIndex++;
        }
        if (str.charAt(0) == '-') {
            isNegative = true;
        }
        for (int i = startIndex; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch < '0' || ch > '9') {
                break;
            }
            digit = ch - '0';
            result = result * 10 + digit;
        }
        if (isNegative) {
            result = -result;
        }
        if (result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }

    @Test
    @DisplayName("\"42\" -> 42")
    void test0() {
        assertEquals(42, myAtoi("42"));
    }

    @Test
    @DisplayName("\"   -42\" -> -42")
    void test1() {
        assertEquals(-42, myAtoi("   -42"));
    }

    @Test
    @DisplayName("\"4193 with words\" -> 4193")
    void test2() {
        assertEquals(4193, myAtoi("4193 with words"));
    }

    @Test
    @DisplayName("\"words and 987\" -> 0")
    void test3() {
        assertEquals(0, myAtoi("words and 987"));
    }

    @Test
    @DisplayName("\"-91283472332\" -> -2147483648 - Overflow")
    void test4() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-91283472332"));
    }

    @Test
    @DisplayName("\"-2147483649\" -> -2147483648 - Overflow")
    void test5() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-2147483649"));
    }

    @Test
    @DisplayName("\"-2147483650\" -> -2147483648 - Overflow")
    void test6() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-2147483650"));
    }

    @Test
    @DisplayName("\"-2147483648\" -> -2147483648")
    void test7() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-2147483648"));
    }

    @Test
    @DisplayName("\"2147483647\" -> 2147483647")
    void test8() {
        assertEquals(Integer.MAX_VALUE, myAtoi("2147483647"));
    }

    @Test
    @DisplayName("\"2147483648\" -> 2147483647 - Overflow")
    void test9() {
        assertEquals(Integer.MAX_VALUE, myAtoi("2147483648"));
    }

    @Test
    @DisplayName("\"82147483648\" -> 2147483647 - Overflow")
    void test10() {
        assertEquals(Integer.MAX_VALUE, myAtoi("82147483648"));
    }

    @Test
    @DisplayName("\"-6147483648\" -> -2147483648 - Overflow")
    void test11() {
        assertEquals(Integer.MIN_VALUE, myAtoi("-6147483648"));
    }

    @Test
    @DisplayName("\"6147483648\" -> 2147483647 - Overflow")
    void test12() {
        assertEquals(Integer.MAX_VALUE, myAtoi("6147483648"));
    }

    @Test
    @DisplayName("\"+42\" -> 42")
    void test13() {
        assertEquals(42, myAtoi("+42"));
    }

    @Test
    @DisplayName("\"-\" -> 0")
    void test14() {
        assertEquals(0, myAtoi("-"));
    }

    @Test
    @DisplayName("\"+\" -> 0")
    void test15() {
        assertEquals(0, myAtoi("+"));
    }

    @Test
    @DisplayName("\"-a\" -> 0")
    void test16() {
        assertEquals(0, myAtoi("-a"));
    }

    @Test
    @DisplayName("\"+a\" -> 0")
    void test17() {
        assertEquals(0, myAtoi("+a"));
    }

    @Test
    @DisplayName("\"-a12\" -> 0")
    void test18() {
        assertEquals(0, myAtoi("-a12"));
    }

    @Test
    @DisplayName("\"-2\" -> -2")
    void test19() {
        assertEquals(-2, myAtoi("-2"));
    }

    @Test
    @DisplayName("\"1\" -> 1")
    void test20() {
        assertEquals(1, myAtoi("1"));
    }

    @Test
    @DisplayName("\"  \" -> 0")
    void test21() {
        assertEquals(0, myAtoi("  "));
    }
}
