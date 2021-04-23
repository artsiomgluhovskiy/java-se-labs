package org.art.java_core.kotlin.delegate

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

interface A {
    fun foo()
}

class B : A {
    override fun foo() {
        println("Hello from the delegate!")
    }
}

class Delegate(b: B) : A by b

class User {
    var name: String by Delegates.notNull()

    fun init(name: String) {
        this.name = name
    }
}

class LazySample {
    val lazy: String by lazy {
        println("computed")
        "my lazy value"
    }
}

class ObservableUser {
    var name: String by Delegates.observable("No name") { d, old, new ->
        println("$old - $new")
    }
}

class VetoableUser {
    var name: String by Delegates.vetoable("No name") { d, old, new ->
        println("$old - $new")
        false
    }
}

class CustomDelegate(private val key: String) {

    private val cache = mutableMapOf<String, String>()

    operator fun getValue(thisRef: Any?, prop: KProperty<*>) = cache[key]?:""

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        cache[key] = value
    }
}

class ValueHolder {
    var value: String by CustomDelegate("key")
}

fun main() {

    // Test 1: Delegation test
    val b = Delegate(B())
    b.foo()

    // Test 2: Late init test

    val user = User()
    // user.name => IllegalStateException
    user.init("Init name")
    println(user.name)

    // Test 3: Lazy init delegate test
    val sample = LazySample()
    println("lazy = ${sample.lazy}")
    println("lazy = ${sample.lazy}")

    // Test 4: Observable delegate
    val obsUser = ObservableUser()
    obsUser.name = "Carl"

    // Test 5: Vetoable delegate
    val vetUser = VetoableUser()
    vetUser.name = "Veta"
    println(vetUser.name)

    // Test 6: Custom caching delegate
    val valHolder = ValueHolder()
    println(valHolder.value)
    valHolder.value = "New value"
    println(valHolder.value)
    println(valHolder.value)
}
