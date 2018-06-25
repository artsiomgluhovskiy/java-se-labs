package org.art.java_core.generics;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Simple generic Stack implementation.
 * @param <E> a type of contained items.
 */
public class Stack<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private E[] elements;
    private int size = 0;

    @SuppressWarnings("unchecked")      //safe
    public Stack() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        E result = elements[--size];
        elements[size] = null;      //Eliminates memory leak
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
