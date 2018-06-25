package org.art.java_core.design.patterns.template_method;

/**
 * Template method (from GoF) - simple code example.
 */
public class TemplateMethod {

    public static void main(String[] args) {

        A a = new A();
        a.templateMethod();

        B b = new B();
        b.templateMethod();
    }
}

abstract class ClassBase {

    void templateMethod() {
        System.out.print("Before 'differ()' invocation...");
        differ();
        System.out.println("After 'differ()' invocation...");
    }

    abstract void differ();
}

class A extends ClassBase {
    void differ() {
        System.out.print("Class A");
    }
}

class B extends ClassBase {
    void differ() {
        System.out.print("Class B");
    }
}