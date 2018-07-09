package org.art.java_core.design.patterns.singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

/**
 * Singleton pattern (from GoF) - simple code examples.
 *
 * Singleton crashing test (via Reflection API).
 */
public class Singleton {

    private static final Logger LOGGER = LogManager.getLogger(Singleton.class);

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        Singleton1 s1 = Singleton1.getInstance();
        Singleton2 s2 = Singleton2.getInstance();
        Singleton3 s3 = Singleton3.INSTANCE;
        Singleton4 s4 = Singleton4.getInstance();
        Singleton5 s5 = Singleton5.getInstance();
        Singleton6 s6 = Singleton6.getInstance();

        //Crashing a Singleton via Reflection API
        Singleton1 newS1Instance = SingletonCrasher.crashSingleton(Singleton1.class);
        LOGGER.debug("Same objects: {}", (s1 == newS1Instance));
    }
}

class Singleton1 {

    private static Singleton1 instance;

    private Singleton1() {}

    public static Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }
}

class Singleton2 {

    private static final Singleton2 INSTANCE = new Singleton2();

    private Singleton2() {}

    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}

enum Singleton3 {

    INSTANCE
}

class Singleton4 {

    private static Singleton4 instance = null;

    private Singleton4() {}

    public static synchronized Singleton4 getInstance() {
        if (instance == null) {
            instance = new Singleton4();
        }
        return instance;
    }
}

class Singleton5 {

    private static volatile Singleton5 instance;

    public static Singleton5 getInstance() {
        Singleton5 localVar = instance;
        if (localVar == null) {
            synchronized (Singleton5.class) {
                localVar = instance;
                if (localVar == null) {
                    instance = localVar = new Singleton5();
                }
            }
        }
        return localVar;
    }
}

//NOTE: Lazy initialization here (in accordance with a new memory model introduced in Java 5)!
class Singleton6 {

    private Singleton6() {}

    private static class SingletonHolder {
        static final Singleton6 HOLDER_INSTANCE = new Singleton6();
    }

    public static Singleton6 getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
