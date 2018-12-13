package org.art.java_core.groovy

class MethodInjectionTest {

    static void main(String[] args) {
        def person1 = new Person(name: "John Smith", age: 22, salary: 1234d)
        println person1
        injectValidateMethod()
        person1.validate()

        def person2 = new Person(age: 22, salary: 1234d)
        println person2
        person2.validate()
    }

    static void injectValidateMethod() {
        Person.metaClass.validate = { ->
            delegate.properties.each {property, value ->
                if (value == null) {
                    println "Entity validation failed: Property $property is null!"
                    throw new IllegalArgumentException()
                }
            }
            println "Entity validation succeed!"
        }
    }
}
