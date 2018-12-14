package org.art.java_core.javaassist;

import java.lang.instrument.Instrumentation;

/**
 * Simple java agent, which transforms class via Javaassist API.
 *
 * Use the following command to run exec jar with a java agent:
 * java -javaagent:java-se-labs-1.0-SNAPSHOT.jar -jar java-se-labs-1.0-SNAPSHOT.jar
 */
public class TransformerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Transformer Agent (URL sniffer) is running...");
        SimpleClassTransformer transformer = new SimpleClassTransformer();
        inst.addTransformer(transformer);
    }
}
