package org.art.java_core.complitable_future.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

public class FallbackStubClient implements StackOverflowClient {

    private static final Logger LOGGER = LogManager.getLogger(FallbackStubClient.class);

    private final StackOverflowClient target;

    public FallbackStubClient(StackOverflowClient target) {
        this.target = target;
    }

    @Override
    public String mostRecentQuestionAbout(String tag) {
        try {
            return target.mostRecentQuestionAbout(tag);
        } catch (Exception e) {
            LOGGER.warn("Problem retrieving tag {}", tag, e);
            switch(tag) {
                case "java":
                    return "How to generate xml report with maven depencency?";
                case "scala":
                    return "Update a timestamp SettingKey in an sbt 0.12 task";
                case "groovy":
                    return "Reusing Grails variables inside Config.groovy";
                case "clojure":
                    return "Merge two comma delimited strings in Clojure";
                default:
                    throw e;
            }
        }
    }

    @Override
    public Document mostRecentQuestionsAbout(String tag) {
        try {
            return target.mostRecentQuestionsAbout(tag);
        } catch (Exception e) {
            LOGGER.warn("Problem retrieving recent question {}", tag, e);
            throw new RuntimeException(e);
        }
    }
}
