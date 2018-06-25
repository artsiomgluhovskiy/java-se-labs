package org.art.java_core.generics;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Heterogeneous type safe container implementation (from Effective Java).
 * Type token idiom is implemented here.
 */
public class HeterogeneousContainer {

    private Map<Class<?>, Object> container = new HashMap<>();

    public <T> void putItem(Class<T> type, T item) {
        Objects.requireNonNull(type);
        container.put(type, item);
    }

    public <T> T getItem(Class<T> type) {
        Objects.requireNonNull(type);
        return type.cast(container.get(type));
    }

    @Override
    public String toString() {
        return "HeterogeneousContainer{" +
                "container=" + container +
                '}';
    }
}
