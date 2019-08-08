package org.art.java_core.algorithms.leetcode.utils;

public class ArraysUtils {

    private ArraysUtils() {
    }

    public static boolean arrayBeginsPrecisely(int[] srcArray, int[] beginsWith) {
        if (srcArray == null || beginsWith == null) {
            return false;
        }
        if (beginsWith.length > srcArray.length) {
            return false;
        }
        for (int i = 0; i < beginsWith.length; i++) {
            if (beginsWith[i] != srcArray[i]) {
                return false;
            }
        }
        return true;
    }
}
