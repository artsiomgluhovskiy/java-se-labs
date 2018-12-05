package org.art.java_core.instrumentation;

import java.lang.instrument.Instrumentation;

/**
 * Simple utility class, which uses Instrumentation API
 * in order to define a size of objects in Java.
 *
 * Use the following command to run exec jar with a java agent:
 * java -javaagent:java-se-labs-1.0-SNAPSHOT.jar -jar java-se-labs-1.0-SNAPSHOT.jar
 */
public class ObjectSizeFetcher {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        long objSize = 0;
        if (instrumentation != null) {
            objSize = instrumentation.getObjectSize(o);
        }
        return objSize;
    }
}
