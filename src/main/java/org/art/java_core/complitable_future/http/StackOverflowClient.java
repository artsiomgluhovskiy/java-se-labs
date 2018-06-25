package org.art.java_core.complitable_future.http;

import org.jsoup.nodes.Document;

public interface StackOverflowClient {
    String mostRecentQuestionAbout(String tag);
    Document mostRecentQuestionsAbout(String tag);
}
