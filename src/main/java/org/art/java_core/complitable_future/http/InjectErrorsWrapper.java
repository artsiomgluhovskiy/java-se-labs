package org.art.java_core.complitable_future.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InjectErrorsWrapper implements StackOverflowClient {
    private static final Logger LOGGER = LogManager.getLogger(InjectErrorsWrapper.class);

    private final StackOverflowClient target;
    private final Set<String> blackList;

    public InjectErrorsWrapper(StackOverflowClient target, String... blackList) {
        this.target = target;
        this.blackList = new HashSet<>(Arrays.asList(blackList));
    }

    @Override
    public String mostRecentQuestionAbout(String tag) {
        throwIfBlackListed(tag);
        return target.mostRecentQuestionAbout(tag);
    }

    @Override
    public Document mostRecentQuestionsAbout(String tag) {
        throwIfBlackListed(tag);
        return target.mostRecentQuestionsAbout(tag);
    }

    private void throwIfBlackListed(String tag) {
        if (blackList.contains(tag)) {
            ArtificialSleepWrapper.artificialSleep(400);
            LOGGER.warn("About to throw artificial exception due to: {}", tag);
            throw new IllegalArgumentException("Unsupported " + tag);
        }
    }
}
