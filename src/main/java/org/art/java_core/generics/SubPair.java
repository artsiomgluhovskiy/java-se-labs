package org.art.java_core.generics;

/**
 * Extended version of Pair container.
 */
public class SubPair extends Pair<String, String> {

    private int value;

    public SubPair(String a, String b, int num) {
        super(a, b);
        this.value = num;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SubPair{" +
                super.toString() +
                "value = " + value +
                '}';
    }
}
