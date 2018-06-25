package org.art.java_core.complitable_future.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class LoadFromStackOverflowTask implements Callable<String> {

    private static final Logger LOGGER = LogManager.getLogger(LoadFromStackOverflowTask.class);

    private final StackOverflowClient client;
    private final String tag;

    public LoadFromStackOverflowTask(StackOverflowClient client, String tag) {
        this.client = client;
        this.tag = tag;
    }

    @Override
    public String call() throws Exception {
        return client.mostRecentQuestionAbout(tag);
    }
}
