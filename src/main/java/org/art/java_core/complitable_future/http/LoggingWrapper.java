package org.art.java_core.complitable_future.http;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

public class LoggingWrapper implements StackOverflowClient {

    private static final Logger LOGGER = LogManager.getLogger(LoggingWrapper.class);

    private final StackOverflowClient target;

    public LoggingWrapper(StackOverflowClient target) {
        this.target = target;
    }

    @Override
    public String mostRecentQuestionAbout(String tag) {
        LOGGER.debug("Entering mostRecentQuestionAbout({})", tag);
        final String title = target.mostRecentQuestionAbout(tag);
        LOGGER.debug("Leaving mostRecentQuestionAbout({}): {}", tag, title);
        return title;
    }

    @Override
    public Document mostRecentQuestionsAbout(String tag) {
        LOGGER.debug("Entering mostRecentQuestionsAbout({})", tag);
        final Document document = target.mostRecentQuestionsAbout(tag);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Leaving mostRecentQuestionsAbout({}): {}", tag, htmlExcerpt(document));
        }
        return document;
    }

    private String htmlExcerpt(Document document) {
        final String outerHtml = document.outerHtml();
        final Iterable<String> lines = Splitter.onPattern("\r?\n").split(outerHtml);
        final Iterable<String> firstLines = Iterables.limit(lines, 4);
        final String excerpt = Joiner.on(' ').join(firstLines);
        final int remainingBytes = Math.max(outerHtml.length() - excerpt.length(), 0);
        return excerpt + " [...and " + remainingBytes + " chars]";
    }
}
