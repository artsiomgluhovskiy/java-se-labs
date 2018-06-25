package org.art.java_core.codewars;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

/**
 * Max different length finder implementation.
 */
class MaxDiffLength {

    static int maxDiffLn(String[] a1, String[] a2) {
        IntSummaryStatistics stat1 = Arrays.stream(a1)
                .mapToInt(String::length)
                .summaryStatistics();
        IntSummaryStatistics stat2 = Arrays.stream(a2)
                .mapToInt(String::length)
                .summaryStatistics();
        if (stat1.getCount() == 0 || stat2.getCount() == 0) {
            return -1;
        }
        int res1 = Math.abs(stat1.getMax() - stat2.getMin());
        int res2 = Math.abs(stat2.getMax() - stat1.getMin());
        return res1 > res2 ? res1 : res2;
    }

    public static void main(String[] args) {
        String[] s1 = {"hello", "definition"};
        String[] s2 = {"hello2", "definition2"};
        System.out.println(maxDiffLn(s1, s2));
    }
}
