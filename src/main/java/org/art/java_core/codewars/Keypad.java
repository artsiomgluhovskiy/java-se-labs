package org.art.java_core.codewars;

import java.util.*;

/**
 * Mobile Keypad quiz.
 */
class Keypad {

    static final Map<Character, List<Character>> keyMap;

    //Keypad initializing
    static {
        keyMap = new HashMap<>(12);
        keyMap.put('0', of(' '));
        keyMap.put('1', null);
        keyMap.put('2', of('A', 'B', 'C'));
        keyMap.put('3', of('D', 'E', 'F'));
        keyMap.put('4', of('G', 'H', 'I'));
        keyMap.put('5', of('J', 'K', 'L'));
        keyMap.put('6', of('M', 'N', 'O'));
        keyMap.put('7', of('P', 'Q', 'R', 'S'));
        keyMap.put('8', of('T', 'U', 'V'));
        keyMap.put('9', of('W', 'X', 'Y', 'Z'));
        keyMap.put('*', null);
        keyMap.put('#', null);
    }

    static List<Character> of(Character... values) {
        return Arrays.asList(values);
    }

    static int presses(String phrase) {
        int presses = 0;
        char[] chars = phrase.toUpperCase().toCharArray();
        for (char ch : chars) {
            presses += getPressesForLetter(ch);
        }
        return presses;
    }

    static int getPressesForLetter(char ch) {
        if (keyMap.containsKey(ch)) {
            List<Character> values = keyMap.get(ch);
            if (values == null) {
                return 1;
            } else {
                return values.size() + 1;
            }
        } else {
            Collection<List<Character>> values = keyMap.values();
            for (List<Character> charList : values) {
                if (charList != null) {
                    int valueCounter = 0;
                    for (Character character : charList) {
                        valueCounter++;
                        if (ch == character) {
                            return valueCounter;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String testString1 = "LOL";
        String testString2 = "HOW R U";
        String testString3 = "13452*";
        System.out.println(presses(testString3));
    }
}
