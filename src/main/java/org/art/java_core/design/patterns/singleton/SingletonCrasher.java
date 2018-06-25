package org.art.java_core.design.patterns.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Singleton Crasher implementation.
 */
class SingletonCrasher {

    @SuppressWarnings("unchecked")
    static <S> S crashSingleton(Class<S> singletonClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        S newInstance = null;
        Constructor<S>[] constructors = (Constructor<S>[]) singletonClass.getDeclaredConstructors();
        if (constructors.length != 0) {
            Constructor<S> constructor = constructors[0];
            if (constructor != null) {
                constructor.setAccessible(true);
                newInstance = constructor.newInstance();
            }
        }
        return newInstance;
    }
}
