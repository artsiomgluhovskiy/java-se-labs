package org.art.java_core.design.patterns.iterator;

import java.util.Arrays;

/**
 * Iterator pattern (from GoF) - code example.
 * Simple task iterator implementation.
 */
public class MyIterator {
    public static void main(String[] args) {

        String[] niceToDo = {"To build a house.", "To grow up son.", "To plant a tree."};
        TaskContainer<String> tasks = new TaskContainer<>(niceToDo);
        Iterator<String> iterator = tasks.getIterator();
        while (iterator.hasNext()) {
            System.out.println("Task: " + iterator.next());
        }
    }
}

interface Iterator<T> {

    boolean hasNext();
    T next();
}

interface Iterable<T> {

    Iterator<T> getIterator();
}

class TaskContainer<T> implements Iterable<T> {

    private T[] box;

    TaskContainer(T[] box) {
        this.box = box;
    }

    @Override
    public Iterator<T> getIterator() {
        return new TaskIterator();
    }

    private class TaskIterator implements Iterator<T> {

        int index = 0;

        @Override
        public boolean hasNext() {
            if (index < box.length) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            return box[index++];
        }
    }

    @Override
    public String toString() {
        return "ConcreteAggregade{" +
                "box=" + Arrays.toString(box) +
                '}';
    }
}
