package org.art.java_core.codewars;

import java.util.StringJoiner;

/**
 * Progression quiz.
 */
class Progression {

    static String arithmeticSequenceElements(int first, int step, long total) {
        StringJoiner joiner = new StringJoiner(", ");
        int counter = 0;
        for (int i = first; counter < total; i += step) {
            joiner.add(String.valueOf(i));
            counter++;
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        String result = arithmeticSequenceElements(1, 1, 10);
        System.out.println(result);
    }
}