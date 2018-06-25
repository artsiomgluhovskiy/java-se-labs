package org.art.java_core.concurrent.scheduling;

import java.util.concurrent.ThreadFactory;

/**
 * Custom thread factory implementation.
 */
public class CustomThreadFactory implements ThreadFactory {

    private String factoryName;
    private int threadId = 1;

    public CustomThreadFactory(String name) {
        this.factoryName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        factoryName += threadId++;
        return new Thread(r, factoryName);
    }
}
