package org.art.java_core.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

var continuation: Continuation<String>? = null

fun main(args: Array<String>) {
    val job = GlobalScope.launch(Dispatchers.Unconfined) {
        while(true) {
            println(suspendHere())
        }
    }
    println("Hello")
    continuation?.resume("Resumed first time")
    continuation?.resume("Resumed second time")
}

suspend fun suspendHere() = suspendCancellableCoroutine<String> {
    continuation = it
}
