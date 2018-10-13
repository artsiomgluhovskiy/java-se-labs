package org.art.java_core.flow;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

public class EndSubscriber<T> implements Flow.Subscriber<T> {

    private AtomicInteger howMuchMessagesConsume;
    private Flow.Subscription subscription;
    private List<T> consumedElements = Collections.synchronizedList(new LinkedList<>());

    public EndSubscriber(Integer howMuchMessagesConsume) {
        this.howMuchMessagesConsume = new AtomicInteger(howMuchMessagesConsume);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Subscriber subscribed!");
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        howMuchMessagesConsume.decrementAndGet();
        System.out.println("Got: " + item);
        System.out.println("Subscriber 'onNext()' method thread: " + Thread.currentThread().getName());
        consumedElements.add(item);
        if (howMuchMessagesConsume.get() > 0) {
            subscription.request(1);
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error!");
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }

    public List<T> getConsumedElements() {
        return consumedElements;
    }
}
