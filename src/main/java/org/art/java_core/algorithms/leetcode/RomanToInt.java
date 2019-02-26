package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Roman to integer" quiz solution from LeetCode.
 */
public class RomanToInt {

    private final Map<Character, Integer> romanNumbers = new HashMap<>();

    {
        romanNumbers.put('I', 1);
        romanNumbers.put('V', 5);
        romanNumbers.put('X', 10);
        romanNumbers.put('L', 50);
        romanNumbers.put('C', 100);
        romanNumbers.put('D', 500);
        romanNumbers.put('M', 1000);
    }

    public int romanToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int len = s.length();
        int result = 0;
        int number;
        int prevNumber = 0;
        int index = len - 1;
        while (index >= 0) {
            number = romanNumbers.get(s.charAt(index));
            if (number >= prevNumber) {
                result = result + number;
            } else {
                result = result - number;
            }
            prevNumber = number;
            index--;
        }
        return result;
    }

    @Test
    @DisplayName("\"III\" -> 3")
    void test0() {
        assertEquals(3, romanToInt("III"));
    }

    @Test
    @DisplayName("\"IV\" -> 4")
    void test1() {
        assertEquals(4, romanToInt("IV"));
    }

    @Test
    @DisplayName("\"IX\" -> 9")
    void test2() {
        assertEquals(9, romanToInt("IX"));
    }

    @Test
    @DisplayName("\"LVIII\" -> 58")
    void test3() {
        assertEquals(58, romanToInt("LVIII"));
    }

    @Test
    @DisplayName("\"MCMXCIV\" -> 1994")
    void test4() {
        assertEquals(1994, romanToInt("MCMXCIV"));
    }

    @Test
    @DisplayName("\"I\" -> 1")
    void test5() {
        assertEquals(1, romanToInt("I"));
    }

    @Test
    @DisplayName("\"XIX\" -> 19")
    void test6() {
        assertEquals(19, romanToInt("XIX"));
    }

    @Test
    @DisplayName("\"MMI\" -> 2001")
    void test7() {
        assertEquals(2001, romanToInt("MMI"));
    }
}
