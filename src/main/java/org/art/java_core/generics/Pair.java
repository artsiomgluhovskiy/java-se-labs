package org.art.java_core.generics;

/**
 * Simple mutable container which can store two objects
 * of different types (in general).
 */
public class Pair<K, V> {

    private K a;
    private V b;

    public Pair(K a, V b) {
        this.a = a;
        this.b = b;
    }

    public void setA(K a) {
        this.a = a;
    }

    public void setB(V b) {
        this.b = b;
    }

    public K getA() {
        return this.a;
    }

    public V getB() {
        return this.b;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "a = " + a +
                ", b = " + b +
                '}';
    }
}
