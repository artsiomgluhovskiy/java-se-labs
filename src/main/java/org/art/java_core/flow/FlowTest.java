package org.art.java_core.flow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {

    @Test
    @DisplayName("Simple publisher/subscriber test")
    void test1() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        List<String> items = List.of("message 1", "message 2", "message 3", "message 4");
        EndSubscriber<String> subscriber = new EndSubscriber<>(items.size());
        publisher.subscribe(subscriber);

        assertEquals(publisher.getNumberOfSubscribers(), 1);
        items.forEach(publisher::submit);
        publisher.close();

        System.out.println("Test method thread: " + Thread.currentThread().getName());
        Thread.sleep(500);

        assertSame(items.size(), subscriber.getConsumedElements().size());
        assertIterableEquals(items, subscriber.getConsumedElements());
    }

    @Test
    @DisplayName("Publisher/subscriber test with Processor")
    void test2() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        TransformProcessor<String, Integer> transformProcessor = new TransformProcessor<>(Integer::parseInt);
        List<String> items = List.of("1", "2", "3");
        List<Integer> expectedResult = List.of(1, 2, 3);
        EndSubscriber<Integer> subscriber = new EndSubscriber<>(expectedResult.size());

        publisher.subscribe(transformProcessor);
        transformProcessor.subscribe(subscriber);
        items.forEach(publisher::submit);
        publisher.close();

        System.out.println("Test method thread: " + Thread.currentThread().getName());
        Thread.sleep(500);

        assertSame(expectedResult.size(), subscriber.getConsumedElements().size());
        assertIterableEquals(expectedResult, subscriber.getConsumedElements());
    }
}
