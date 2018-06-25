package org.art.java_core.codewars;

import java.util.Set;
import java.util.TreeSet;

/**
 * The longest unique ordered char sequence quiz.
 */
class TwoToOne {

    static String longest1(String s1, String s2) {
        String s = s1 + s2;
        char[] charArr = s.toCharArray();
        Set<Character> chars = new TreeSet<>();
        for (char anArr1 : charArr) {
            chars.add(anArr1);
        }
        StringBuilder sb = new StringBuilder(charArr.length);
        chars.forEach(sb::append);
        return sb.toString();
    }

    //More preferable way
    static String longest2(String s1, String s2) {
        String s = s1 + s2;
        return s.chars().distinct().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static void main(String[] args) {
        String s1 = "String 1 Hello";
        String s2 = "String 2 Hello";
        System.out.println(longest1(s1, s2));
    }
}

