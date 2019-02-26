package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Reverse Integer" quiz solution from LeetCode.
 */
public class ReverseInteger {

    public int reverse(int x) {
        int reversed = 0;
        while (x != 0) {
            int tmp = reversed * 10 + x % 10;
            x = x / 10;
            if (reversed != tmp / 10) {
                return 0;
            }
            reversed = tmp;
        }
        return reversed;
    }

    @Test
    @DisplayName("123 -> 321")
    void test0() {
        assertEquals(321, reverse(123));
    }

    @Test
    @DisplayName("-123 -> -321")
    void test1() {
        assertEquals(-321, reverse(-123));
    }

    @Test
    @DisplayName("120 -> 21")
    void test2() {
        assertEquals(21, reverse(120));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE -> 0")
    void test3() {
        assertEquals(0, reverse(Integer.MIN_VALUE));
    }

    @Test
    @DisplayName("10 -> 1")
    void test4() {
        assertEquals(1, reverse(10));
    }

    @Test
    @DisplayName("3 -> 3")
    void test5() {
        assertEquals(3, reverse(3));
    }

    @Test
    @DisplayName("1534236469 -> 0")
    void test6() {
        assertEquals(0, reverse(1534236469));
    }

    @Test
    @DisplayName("-2147483412 -> -2143847412")
    void test7() {
        assertEquals(-2143847412, reverse(-2147483412));
    }
}
