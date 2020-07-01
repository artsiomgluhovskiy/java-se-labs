package org.art.java_core.rxjava

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.BehaviorSubject
import kotlin.concurrent.thread

/**
 * Simple example to show thread managing in RxJava (subscribeOn, observeOn, etc.)
 */
fun main() {

    // subscribeOn example with a simple emitter
    // emitting and mapping in a scheduler thread
    runSubscribeOnExampleWithSimpleEmitter()

    println()

    // subscribeOn example with an emitter running in a separate thread
    // emitting and mapping in an emitter thread
    runSubscribeOnExampleWithSeparateThreadEmitter()

    println()

    // observeOn example with an emitter running in a separate thread
    // emitting is in emitter thread and mapping is in a scheduler thread
    runObserveOnExampleWithSeparateThreadEmitter()

    println()

    // run subject example
    runSubjectExample()

    println()

    // run behavior subject example
    runBehaviorSubjectExample()
}

private fun runSubscribeOnExampleWithSimpleEmitter() {
    val observable = Observable.create<Int> { emitter ->
        println("${Thread.currentThread()} Subscribing to observable with simple emitter (subscribeOn)")
        println("${Thread.currentThread()} 1 - emitting")
        emitter.onNext(1)
        println("${Thread.currentThread()} 2 - emitting")
        emitter.onNext(2)
        println("${Thread.currentThread()} 3 - emitting")
        emitter.onNext(3)
        emitter.onComplete()
    }

    subscribeToObservable(observable)
}

private fun runSubscribeOnExampleWithSeparateThreadEmitter() {
    val observable = Observable.create<Int> { emitter ->
        println("${Thread.currentThread()} Subscribing to observable with an emitter in a separate thread (subscribeOn)")
        thread(name = "Main Thread", isDaemon = false) {
            println("${Thread.currentThread()} 1 - emitting")
            emitter.onNext(1)
            println("${Thread.currentThread()} 2 - emitting")
            emitter.onNext(2)
            println("${Thread.currentThread()} 3 - emitting")
            emitter.onNext(3)
            emitter.onComplete()
        }
    }

    subscribeToObservable(observable)
}

private fun subscribeToObservable(observable: Observable<Int>) {
    observable.subscribeOn(Schedulers.computation())
            .map {
                println("${Thread.currentThread()} $it - performing a computation")
                it * 10
            }.test()
            .awaitTerminalEvent()
}

private fun runObserveOnExampleWithSeparateThreadEmitter() {
    val observable = Observable.create<Int> { emitter ->
        println("${Thread.currentThread()} Subscribing to observable with an emitter in a separate thread (observeOn)")
        thread(name = "Main Thread", isDaemon = false) {
            println("${Thread.currentThread()} 1 - emitting")
            emitter.onNext(1)
            println("${Thread.currentThread()} 2 - emitting")
            emitter.onNext(2)
            println("${Thread.currentThread()} 3 - emitting")
            emitter.onNext(3)
            emitter.onComplete()
        }
    }

    observable.observeOn(Schedulers.computation())
            .map {
                println("${Thread.currentThread()} $it - performing a computation")
                it * 10
            }.test()
            .awaitTerminalEvent()
}

private fun runSubjectExample() {
    val subject = PublishSubject.create<Int>()

    val observer1 = subject
            .observeOn(Schedulers.io())
            .doOnNext { println("${Thread.currentThread()} $it - It should happen in IO thread") }
            .test()

    val observer2 = subject
            .observeOn(Schedulers.newThread())
            .doOnNext { println("${Thread.currentThread()} $it - It should happen on a new thread") }
            .test()

    Thread.sleep(10)
    subject.onNext(1)
    Thread.sleep(10)
    subject.onNext(2)
    Thread.sleep(10)
    subject.onNext(3)
    subject.onComplete()

    observer1.awaitTerminalEvent()
    observer2.awaitTerminalEvent()
}

private fun runBehaviorSubjectExample() {
    val subject = BehaviorSubject.create<Int>()

    val observer1 = subject
            .subscribeOn(Schedulers.io())
            .doOnNext { println("${Thread.currentThread()} $it - First observer") }
            .test()

    subject.onNext(1) // Will be emitted as part of the subscription

    val observer2 = subject
            .subscribeOn(Schedulers.io())
            .doOnNext { println("${Thread.currentThread()} $it - Second observer") }
            .test()

    val observer3 = subject
            .subscribeOn(Schedulers.newThread())
            .doOnNext { println("${Thread.currentThread()} $it - Third observer") }
            .test()

    Thread.sleep(10)
    subject.onNext(2)
    Thread.sleep(10)
    subject.onNext(3)
    Thread.sleep(10)
    subject.onComplete()

    observer1.awaitTerminalEvent()
    observer2.awaitTerminalEvent()
    observer3.awaitTerminalEvent()
}