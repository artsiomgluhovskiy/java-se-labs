package org.art.java_core.codewars;

import java.util.HashSet;
import java.util.Set;

/**
 * Pangram Checker quiz.
 */
class PangramChecker {

    static boolean check(String sentence) {
        int charNumber = 26;
        Set<Character> charSet = new HashSet<>(charNumber);
        for (char ch : sentence.toUpperCase().toCharArray()) {
            if (ch >= 65 && ch <= 90) {
                charSet.add(ch);
            }
        }
        return charSet.size() == charNumber;
    }

    public static void main(String[] args) {
        String pangram1 = "The quick brown fox jumps over the lazy dog.";
        boolean res = check(pangram1);
        System.out.println(res);
    }
}
