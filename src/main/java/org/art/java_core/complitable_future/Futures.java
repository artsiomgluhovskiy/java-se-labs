package org.art.java_core.complitable_future;

import io.reactivex.Observable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Futures {

    public static <T> CompletableFuture<T> fromSingleObservable(Observable<T> observable) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        observable
                .doOnError(future::completeExceptionally)
                .single(null)
                .doOnSuccess(future::complete);
        return future;
    }

    public static <T> CompletableFuture<List<T>> fromObservable(Observable<T> observable) {
        final CompletableFuture<List<T>> future = new CompletableFuture<>();
        observable
                .doOnError(future::completeExceptionally)
                .toList()
                .doOnSuccess(future::complete);
        return future;
    }

    public static <T> Observable<T> tObservable(CompletableFuture<T> future) {
        return Observable.create(subscriber ->
        future.whenComplete((result, error) -> {
            if (error != null) {
                subscriber.onError(error);
            } else {
                subscriber.onNext(result);
                subscriber.onComplete();
            }
        }));
    }
}
