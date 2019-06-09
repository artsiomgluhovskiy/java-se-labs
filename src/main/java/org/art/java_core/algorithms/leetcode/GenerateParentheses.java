package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "Generate Parentheses" quiz solution from LeetCode.
 */
public class GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        char[] parHolder = new char[n * 2];
        parHolder[0] = '(';
        generateParenthesis0(results, parHolder, 1, 1, 0, n);
        return results;
    }

    private void generateParenthesis0(List<String> results, char[] parHolder, int pos, int opened, int closed, int overall) {
        if (closed == overall) {
            StringBuilder sb = new StringBuilder(parHolder.length);
            for (char ch : parHolder) {
                sb.append(ch);
            }
            results.add(sb.toString());
            return;
        }
        if (opened > closed) {
            parHolder[pos] = ')';
            generateParenthesis0(results, parHolder, pos + 1, opened, closed + 1, overall);
        }
        if (opened < overall) {
            parHolder[pos] = '(';
            generateParenthesis0(results, parHolder, pos + 1, opened + 1, closed, overall);
        }
    }

    @Test
    void test0() {
        int input = 3;
        List<String> expected = Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()");
        List<String> actual = generateParenthesis(input);
        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
    }
}
