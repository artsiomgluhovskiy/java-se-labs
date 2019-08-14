package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Divide Two Integers" quiz solution from LeetCode.
 */
public class DivideTwoIntegers {

    public int divide(int dividend, int divisor) {
        if (divisor == 1) return dividend;
        if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return -dividend;
            }
        }
        if (divisor == dividend) return 1;
        if (divisor == Integer.MIN_VALUE) return 0;
        boolean isNegative = (dividend < 0) ^ (divisor < 0);
        int result = 0;
        if (divisor < 0) divisor = -divisor;
        if (dividend == Integer.MIN_VALUE) {
            result = 1;
            dividend = dividend + divisor;
        }
        if (dividend < 0) dividend = -dividend;
        if (dividend < divisor) return isNegative ? -result : result;
        int shift = 0;
        while (divisor << shift + 1 > 0 && divisor << shift + 1 < dividend) {
            shift++;
        }
        while (divisor <= dividend) {
            if (dividend >= divisor << shift) {
                dividend = dividend - (divisor << shift);
                result = result + (1 << shift);
            }
            shift--;
        }
        return isNegative ? -result : result;
    }

    @Test
    void test0() {
        int dividend = 63;
        int divisor = 13;
        int result = divide(dividend, divisor);
        assertEquals(4, result);
    }

    @Test
    void test1() {
        int dividend = 7;
        int divisor = -3;
        int result = divide(dividend, divisor);
        assertEquals(-2, result);
    }

    @Test
    void test2() {
        int dividend = 10;
        int divisor = 3;
        int result = divide(dividend, divisor);
        assertEquals(3, result);
    }

    @Test
    void test3() {
        int dividend = 2147483647;
        int divisor = 3;
        int result = divide(dividend, divisor);
        assertEquals(715827882, result);
    }

    @Test
    void test4() {
        int dividend = -1010369383;
        int divisor = -2147483648;
        int result = divide(dividend, divisor);
        assertEquals(0, result);
    }

    @Test
    void test5() {
        int dividend = -2147483648;
        int divisor = -1109186033;
        int result = divide(dividend, divisor);
        assertEquals(1, result);
    }
}
