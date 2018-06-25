package org.art.java_core.algorithms.optimization;

import java.util.Arrays;

/**
 * Rod cutting algorithm implementation (from T. Cormen).
 */
public class CutRod {

    /**
     * Simple recursive method (inefficient because of
     * solving the same time repeatedly - O (2^n))
     */
    public static int cutRod(int[] prices, int rodLength) {
        if (rodLength == 0) {
            return 0;
        }
        int revenue = Integer.MIN_VALUE;
        for (int i = 1; i <= rodLength; i++) {
           revenue = Math.max(revenue, prices[i] + cutRod(prices, rodLength - i));
        }
        return revenue;
    }

    /**
     * Dynamic programming approach (with memorization
     * of the already solved sub tasks).
     */
    public static int memorizedCutRod(int[] prices, int rodLength) {
        int[] revenueStorage = new int[rodLength + 1];
        Arrays.fill(revenueStorage, Integer.MIN_VALUE);
        return memorizedCutRodAux(prices, rodLength, revenueStorage);
    }

    private static int memorizedCutRodAux(int[] prices, int rodLength, int[] revenueStorage) {
        int revenue;
        if (revenueStorage[rodLength] >= 0) {
            return revenueStorage[rodLength];
        }
        if (rodLength == 0) {
            revenue = 0;
        } else {
            revenue = Integer.MIN_VALUE;
            for (int i = 1; i <= rodLength; i++) {
                revenue = Math.max(revenue, prices[i] +
                        memorizedCutRodAux(prices, rodLength - i, revenueStorage));
            }
        }
        revenueStorage[rodLength] = revenue;
        return revenue;
    }

    /**
     * Dynamic programming approach (with bottom up method).
     */
    private static int bottomUpCutRod(int[] prices, int rodLength) {
        int[] revenueStorage = new int[rodLength + 1];
        revenueStorage[0] = 0;
        int revenue;
        for (int j = 1; j <= rodLength; j++) {
            revenue = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                revenue = Math.max(revenue, prices[i] + revenueStorage[j - i]);
            }
            revenueStorage[j] = revenue;
        }
        return revenueStorage[rodLength];
    }

    /**
     * Dynamic programming approach (with bottom up method and saving the solution).
     */
    private static int[] bottomUpCutRodExt(int[] prices, int rodLength) {
        int[] revenueStorage = new int[rodLength + 1];
        int[] solution = new int[rodLength + 1];
        revenueStorage[0] = 0;
        int revenue;
        for (int j = 1; j <= rodLength; j++) {
            revenue = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                int res;
                if (revenue < (res = prices[i] + revenueStorage[j - i])) {
                    revenue = res;
                    solution[j] = i;
                }
            }
            revenueStorage[j] = revenue;
        }
        return solution;
    }

    private static void printSolution(int[] solution) {
        StringBuilder sb = new StringBuilder();
        sb.append("Solution: ");
        int first = solution.length - 1;
        sb.append(piecesToString(solution[first])).append(" ");
        while (first > 0) {
            first = first - solution[first];
            sb.append(piecesToString(first));
        }
        System.out.println(sb.toString());
    }

    private static String piecesToString(int pieces) {
        String str = "";
        for (int i = 0; i < pieces; i++) {
            str = str + "-";
        }
        return str;
    }

    public static void main(String[] args) {
        //Rod cutting test (simple recursion)
        int[] prices = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int rodLength1 = 4;
        long start = System.nanoTime();
        int result1 = cutRod(prices, rodLength1);
        long end = System.nanoTime();
        System.out.println("Max revenue (simple recursion): " + result1 + ". Time consumption: " + (end - start));

        //Rod cutting test (with memorization)
        start = System.nanoTime();
        int result2 = memorizedCutRod(prices, rodLength1);
        end = System.nanoTime();
        System.out.println("Max revenue (with memorization): " + result2 + ". Time consumption: " + (end - start));

        //Rod cutting test (with bottom up method)
        start = System.nanoTime();
        int result3 = bottomUpCutRod(prices, rodLength1);
        end = System.nanoTime();
        System.out.println("Max revenue (with bottom up method): " + result3 + ". Time consumption: " + (end - start));

        //Rod cutting test (with bottom up method - extended)
        printSolution(bottomUpCutRodExt(prices, rodLength1));
    }
}
