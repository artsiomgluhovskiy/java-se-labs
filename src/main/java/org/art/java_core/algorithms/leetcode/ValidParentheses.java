package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "Valid Parentheses" quiz solution from LeetCode.
 */
public class ValidParentheses {

    public boolean isValid(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else {
                if (!stack.isEmpty() && isPaired(stack.peek(), ch)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isPaired(char ch1, char ch2) {
        return ch1 == '(' && ch2 == ')' || ch1 == '{' && ch2 == '}' || ch1 == '[' && ch2 == ']';
    }

    @Test
    void test0() {
        assertTrue(isValid("()"));
    }

    @Test
    void test2() {
        assertTrue(isValid("{()}"));
    }

    @Test
    void test3() {
        assertTrue(isValid("{([])}"));
    }

    @Test
    void test4() {
        assertTrue(isValid("{}[]()"));
    }

    @Test
    void test5() {
        assertTrue(isValid("{}[()]()"));
    }

    @Test
    void test6() {
        assertFalse(isValid("{}[(()])()"));
    }
}
