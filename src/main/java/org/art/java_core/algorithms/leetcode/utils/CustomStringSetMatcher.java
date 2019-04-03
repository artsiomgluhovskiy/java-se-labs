package org.art.java_core.algorithms.leetcode.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Custom Harmcrest matcher implementation.
 * Provides equality check for sets of strings (with allowed letters permutation) in the following way:
 *  ["ab", "cd"] matches ["ba", "dc"] => true
 *  ["ab", "cd"] matches ["cd", "ab"] => true
 *  ["ab", "cd"] matches ["dc", "ba"] => true
 *  ["ab", "cd"] matches ["abc", "cd"] => false
 */
public class CustomStringSetMatcher extends TypeSafeMatcher<List<String>> {

    private List<String> thisList;

    public CustomStringSetMatcher(List<String> thisList) {
        this.thisList = thisList;
    }

    @Override
    protected boolean matchesSafely(List<String> thatList) {
        if (thisList == null || thatList == null) {
            return false;
        }
        if (thisList.size() != thatList.size()) {
            return false;
        }
        Set<String> thatSet = new HashSet<>(thatList);
        for (String str : thisList) {
            if (!thatSet.contains(str)) {
                String reversedStr = new StringBuilder(str).reverse().toString();
                if (!thatSet.contains(reversedStr)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(thisList);
    }

    public static Matcher<List<String>> setOfStrings(List<String> strings) {
        return new CustomStringSetMatcher(strings);
    }
}
