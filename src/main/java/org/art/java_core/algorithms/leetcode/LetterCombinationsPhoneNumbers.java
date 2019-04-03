package org.art.java_core.algorithms.leetcode;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.art.java_core.algorithms.leetcode.utils.CustomStringSetMatcher.setOfStrings;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * "Letter Combinations of a Phone Number" quiz solution from LeetCode.
 */
public class LetterCombinationsPhoneNumbers {

    private static final Map<Character, Character[]> MAP = new HashMap<>();

    static {
        MAP.put('2', new Character[]{'a', 'b', 'c'});
        MAP.put('3', new Character[]{'d', 'e', 'f'});
        MAP.put('4', new Character[]{'g', 'h', 'i'});
        MAP.put('5', new Character[]{'j', 'k', 'l'});
        MAP.put('6', new Character[]{'m', 'n', 'o'});
        MAP.put('7', new Character[]{'p', 'q', 'r', 's'});
        MAP.put('8', new Character[]{'t', 'u', 'v'});
        MAP.put('9', new Character[]{'w', 'x', 'y', 'z'});
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        Character[] chars = MAP.get(digits.charAt(0));
        List<String> initStrs = new ArrayList<>();
        for(char ch : chars) {
            initStrs.add(String.valueOf(ch));
        }
        return concat(digits.substring(1), initStrs);
    }

    private List<String> concat(String digits, List<String> accumulator) {
        if (digits.isEmpty()) {
            return accumulator;
        }
        List<String> result = new ArrayList<>();
        Character[] chars = MAP.get(digits.charAt(0));
        for (char ch : chars) {
            for (String s2 : accumulator) {
                result.add(s2 + ch);
            }
        }
        return concat(digits.substring(1), result);
    }

    @Test
    void test0() {
        String input = "23";
        List<String> output = Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf");
        assertThat(output, is(setOfStrings(letterCombinations(input))));
    }

    @Test
    void test1() {
        String input = "5";
        List<String> output = Arrays.asList("j", "k", "l");
        assertThat(output, is(setOfStrings(letterCombinations(input))));
    }

    @Test
    void test2() {
        String input = "9";
        List<String> output = Arrays.asList("w", "x", "y", "z");
        assertThat(output, is(setOfStrings(letterCombinations(input))));
    }

}
