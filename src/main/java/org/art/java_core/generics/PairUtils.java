package org.art.java_core.generics;

import java.util.Objects;

public final class PairUtils {

    /**
     * Creates a new Pair with mutually swapped elements.
     */
    public static <K, V> Pair<V, K> swap(Pair<K, V> pair) {
        Objects.requireNonNull(pair);
        K newB = pair.getA();
        V newA = pair.getB();
        return new Pair<>(newA, newB);
    }
}
