package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * "ZigZag Conversion" quiz solution from LeetCode.
 */
public class ZigZagConversion {

    public String convert(String s, int numRows) {
        List<StringBuilder> rows = new ArrayList<>();
        boolean isDown = true;
        int currentRow = 0;
        if (numRows == 1) {
            return s;
        }
        for (int i = 0; i < Math.min(s.length(), numRows); i++) {
            rows.add(new StringBuilder());
        }
        for (char ch : s.toCharArray()) {
            rows.get(currentRow).append(ch);
            if (currentRow == numRows - 1) {
                isDown = false;
            } else if (currentRow == 0) {
                isDown = true;
            }
            currentRow += isDown ? 1 : -1;
        }
        return rows.stream().reduce(StringBuilder::append).orElse(new StringBuilder()).toString();
    }

    @Test
    @DisplayName("s = PAYPALISHIRING, numRows = 3 -> PAHNAPLSIIGYIR")
    void test0() {
        assertEquals("PAHNAPLSIIGYIR", convert("PAYPALISHIRING", 3));
    }
}
