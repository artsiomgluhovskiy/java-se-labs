package org.art.java_core.complitable_future;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import io.reactivex.Observable;
import org.art.java_core.complitable_future.http.Question;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class TaskLauncher extends AbstractTaskLauncher {

    /**
     * Task ID: 1
     * Blocking method.
     */
    void blockingCall() {
        final String title = client.mostRecentQuestionAbout("java");
        LOGGER.debug("Most recent Java question: '{}'", title);
    }

    /**
     * Task ID: 2
     * Blocking method as well (with the task submission to the ExecutorService).
     */
    void executorService() throws Exception {
        final Callable<String> task = () -> client.mostRecentQuestionAbout("java");
        final Future<String> javaQuestionFuture = executorService.submit(task);
        final String javaQuestion = javaQuestionFuture.get();
        LOGGER.debug("Found: '{}'", javaQuestion);
    }

    /**
     * Task ID: 3
     * Non-blocking method (via Completable Future API with custom ExecutorService).
     */
    void supplyAsyncWithCustomExecutor() throws Exception {
        final CompletableFuture<String> java = CompletableFuture.supplyAsync(
                () -> client.mostRecentQuestionAbout("java"),
                executorService
        );
        LOGGER.debug("Some action before method invocation.");
        LOGGER.debug("Found: '{}'", java.get());
    }

    /**
     * Task ID: 4
     * 'thenApply' chained non-blocking data processing
     * (with synchronous mapping functions).
     */
    void thenApplyChained() throws Exception {
        final CompletableFuture<Document> java = CompletableFuture.supplyAsync(
                () -> client.mostRecentQuestionsAbout("java"),
                executorService);
        final CompletableFuture<Integer> length = java
                .thenApply(doc -> doc.select("a.question-hyperlink").get(0))
                .thenApply(Element::text)
                .thenApply(String::length);
        LOGGER.debug("Some action before method invocation.");
        LOGGER.debug("Length: {}", length.get());
    }

    /**
     * Task ID: 5
     * 'thenCompose' chained non-blocking data processing
     * (with asynchronous mapping functions).
     */
    void thenComposeChained() throws Exception {
        CompletableFuture<Void> result = javaQuestions()
                .thenCompose(this::findMostInterestingQuestion)
                .thenCompose(this::googleAnswer)
                .thenCompose(this::postAnswer)
                .thenAccept(status -> {
                    if (status == 200) {
                        LOGGER.debug("OK");
                    } else {
                        LOGGER.error("Wrong status code: {}", status);
                    }
                });
        LOGGER.debug("Some action before method invocation.");
        LOGGER.debug("Result: {}", result.get());
    }

    /**
     * Task ID: 6
     * Combines the results of two Completable Futures.
     */
    void thenCombine() {
        final CompletableFuture<String> java = questions("java");
        final CompletableFuture<String> scala = questions("scala");

        final CompletableFuture<Integer> both = java
                .thenCombine(scala, (String javaTitle, String scalaTitle) ->
                        javaTitle.length() + scalaTitle.length());
        both.thenAccept(length -> LOGGER.debug("Total length: {}", length));
        LOGGER.debug("After tasks submission.");
    }

    /**
     * Task ID 7
     * Applies the function to the result of the first completed task.
     */
    void either() {
        final CompletableFuture<String> java = questions("java");
        final CompletableFuture<String> scala = questions("scala");

        final CompletableFuture<String> both = java
                .applyToEither(scala, title -> title.toUpperCase());
        both.thenAccept(title -> LOGGER.debug("First {}", title));
        LOGGER.debug("After tasks submission.");
    }

    /**
     * Task ID: 8
     * Non-blocking completion of all tasks.
     */
    void allOf() {
        final CompletableFuture<String> java = questions("java");
        final CompletableFuture<String> scala = questions("scala");
        final CompletableFuture<String> clojure = questions("clojure");
        final CompletableFuture<String> groovy = questions("groovy");

        final CompletableFuture<Void> allCompleted = CompletableFuture
                .allOf(java, scala, clojure, groovy);

        LOGGER.debug("After tasks submission.");

        allCompleted.thenRun(() -> {
            try {
                LOGGER.debug("Loaded: {}", java.get());
                LOGGER.debug("Loaded: {}", scala.get());
                LOGGER.debug("Loaded: {}", clojure.get());
                LOGGER.debug("Loaded: {}", groovy.get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Error while data processing!", e);
            }
        });
        LOGGER.debug("After tasks completion.");
    }

    /**
     * Task ID: 9
     * Non-blocking completion of one task (the first one).
     */
    void anyOf() {
        final CompletableFuture<String> java = questions("java");
        final CompletableFuture<String> scala = questions("scala");
        final CompletableFuture<String> clojure = questions("clojure");
        final CompletableFuture<String> groovy = questions("groovy");

        final CompletableFuture<Object> firstCompleted =
                CompletableFuture
                        .anyOf(java, scala, clojure, groovy);

        firstCompleted.thenAccept((Object result) -> {
            LOGGER.debug("First: {}", result);
        });
        LOGGER.debug("After task completion.");
    }

    /**
     * Task ID: 10
     * Exception throwing within future processing example.
     * Throws exception which is propagated within the pipeline.
     */
    void exceptionShortCircuitFuture() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> questions = questions("php");

        questions.thenApply(r -> {
            LOGGER.debug("Success!");
            return r;
        });
        questions.get();
    }

    /**
     * Task ID: 11
     * Exception handling example 1 (with recovering).
     */
    void handleException() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> questions = questions("php");

        final CompletableFuture<String> recovered = questions
                .handle((result, throwable) -> {
                    if (throwable != null) {
                        return "No PHP today due to: " + throwable;
                    } else {
                        return result.toUpperCase();
                    }
                });
        LOGGER.debug("Handled: {}", recovered.get());
    }

    /**
     * Task ID: 12
     * Exception handling example 2 (with recovering).
     */
    void shouldHandleExceptionally() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> questions = questions("php");

        final CompletableFuture<String> recovered = questions
                .exceptionally(throwable -> "Sorry, try again later");

        LOGGER.debug("Done: {}", recovered.get());
    }

    /**
     * Task ID: 13
     * Asynchronous HTTP Client Implementation (with callbacks).
     */
    void asyncHttpWithCallbacks() {
        loadTag(
                "java",
                response -> LOGGER.debug("Got: {}", response),
                throwable -> LOGGER.error("Error!", throwable)
        );
    }

    /**
     * Task ID: 14
     * Asynchronous HTTP Client Implementation (with Completable Futures).
     */
    void asyncHttpWithFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> response = loadTag("java");
        LOGGER.debug("Response: {}", response.get());
    }

    /**
     * Task ID: 15
     * Simple RxJava example (push back pattern).
     */
    void pushExample() {
        final List<String> list = Arrays.asList("Java", "Scala", "Clojure", "Groovy");
        final Observable<String> observable = Observable.fromIterable(list);

        observable.subscribe(
                message -> LOGGER.debug("Item: {}", message),
                exc -> LOGGER.error("Exception: {}", exc),
                () -> LOGGER.debug("Processing completed!"));
    }

    /* --- Additional methods for data retrieving --- */
    private CompletableFuture<Document> javaQuestions() {
        return CompletableFuture.supplyAsync(() ->
                        client.mostRecentQuestionsAbout("java"),
                executorService
        );
    }

    private CompletableFuture<String> questions(String tag) {
        return CompletableFuture.supplyAsync(() ->
                        client.mostRecentQuestionAbout(tag),
                executorService);
    }

    private CompletableFuture<Question> findMostInterestingQuestion(Document document) {
        return CompletableFuture.completedFuture(new Question());
    }

    private CompletableFuture<String> googleAnswer(Question q) {
        return CompletableFuture.completedFuture("42");
    }

    private CompletableFuture<Integer> postAnswer(String answer) {
        return CompletableFuture.completedFuture(200);
    }

    void loadTag(String tag, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        asyncHttpClient.prepareGet("http://stackoverflow.com/questions/tagged/" + tag)
                .execute(new AsyncCompletionHandler<Void>() {

                    @Override
                    public Void onCompleted(Response response) throws Exception {
                        onSuccess.accept(response.getResponseBody());
                        return null;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        onError.accept(t);
                    }
                });
    }

    CompletableFuture<String> loadTag(String tag) {
        final CompletableFuture<String> promise = new CompletableFuture<>();
        asyncHttpClient.prepareGet("http://stackoverflow.com/questions/tagged/" + tag)
                .execute(new AsyncCompletionHandler<Void>() {

                    @Override
                    public Void onCompleted(Response response) throws Exception {
                        promise.complete(response.getResponseBody());
                        return null;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        promise.completeExceptionally(t);
                    }
                });
        return promise;
    }

    public static void main(String[] args) throws Exception {

        TaskLauncher launcher = new TaskLauncher();
        int taskId = launcher.getTaskId();
        while (taskId != 0) {
            switch (taskId) {
                case 1:
                    launcher.blockingCall();
                    break;
                case 2:
                    launcher.executorService();
                    break;
                case 3:
                    launcher.supplyAsyncWithCustomExecutor();
                    break;
                case 4:
                    launcher.thenApplyChained();
                    break;
                case 5:
                    launcher.thenComposeChained();
                    break;
                case 6:
                    launcher.thenCombine();
                    break;
                case 7:
                    launcher.either();
                    break;
                case 8:
                    launcher.allOf();
                    break;
                case 9:
                    launcher.anyOf();
                    break;
                case 10:
                    //Danger!!! Throws Exception!!!
                    launcher.exceptionShortCircuitFuture();
                    break;
                case 11:
                    launcher.handleException();
                    break;
                case 12:
                    launcher.shouldHandleExceptionally();
                    break;
                case 13:
                    launcher.asyncHttpWithCallbacks();
                    break;
                case 14:
                    launcher.asyncHttpWithFutures();
                    break;
                case 15:
                    launcher.pushExample();
                    break;
                default:
                    System.out.println("Wrong Task Id. Try again.");
            }
            taskId = launcher.getTaskId();
        }
        System.out.println("Program exit.");
        launcher.shutDownAll();
    }
}
