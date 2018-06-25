package org.art.java_core.codewars;

/**
 * Rotation quiz.
 */
class Rotation {

    static int shiftedDiff(String first, String second) {
        if (first.length() != second.length()) {
            return -1;
        } else if (first.equals(second)) {
            return 0;
        } else {
            StringBuilder sb = new StringBuilder(first.length());
            int counter = 0;
            for (int i = 0; i < first.length(); i++) {
                sb.append(first.charAt(i));
                if (checkContaining(sb.toString(), second)) {
                    counter++;
                } else if (counter == 0) {
                    return -1;
                } else if (checkCorresponding(first, second, counter)) {
                    return first.length() - counter;
                }
            }
        }
        return -1;
    }

    static boolean checkContaining(String str, String compared) {
        return compared.contains(str);
    }

    static boolean checkCorresponding(String first, String second, int counter) {
        for (int i = first.length() - 1; i > counter - 1; i--) {
            if (first.charAt(i) != second.charAt(i - counter)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(shiftedDiff("hoop", "pooh"));
        System.out.println(shiftedDiff("coffee", "eecoff"));
        System.out.println(shiftedDiff("eecoff", "coffee"));
    }
}
