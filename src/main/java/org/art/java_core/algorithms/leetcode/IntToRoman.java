package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Integer to roman" quiz solution from LeetCode.
 */
public class IntToRoman {

    public String intToRoman(int num) {
        int[] arabics = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < romans.length; i++) {
            while (num - arabics[i] >= 0) {
                sb.append(romans[i]);
                num = num - arabics[i];
            }
        }
        return sb.toString();
    }

    @Test
    @DisplayName("3 -> III")
    void test0() {
        assertEquals("III", intToRoman(3));
    }

    @Test
    @DisplayName("500 -> D")
    void test1() {
        assertEquals("D", intToRoman(500));
    }

    @Test
    @DisplayName("400 -> CD")
    void test2() {
        assertEquals("CD", intToRoman(400));
    }

    @Test
    @DisplayName("440 -> CDXL")
    void test3() {
        assertEquals("CDXL", intToRoman(440));
    }

    @Test
    @DisplayName("1 -> I")
    void test4() {
        assertEquals("I", intToRoman(1));
    }

    @Test
    @DisplayName("9 -> IX")
    void test5() {
        assertEquals("IX", intToRoman(9));
    }

    @Test
    @DisplayName("58 -> LVIII")
    void test6() {
        assertEquals("LVIII", intToRoman(58));
    }

    @Test
    @DisplayName("1994 -> MCMXCIV")
    void test7() {
        assertEquals("MCMXCIV", intToRoman(1994));
    }
}
