package org.art.java_core.reflection_api.class_analyzer;

import java.io.PrintStream;

/**
 * Class Analyzer Test.
 */
public class ClassAnalyzerTest {

    public static void main(String[] args) {

        ClassAnalyzer analyzer = new ClassAnalyzer();

        System.out.println("*** 'TestClass' analysis...\n");
        analyzer.analyze(TestClass.class);
        printEmptyLines(2);

        System.out.println("*** 'InnerClass' analysis...\n");
        /*
          NOTE: inner class instance has an invisible link field
          to the outer instance (Class Analyzer reveals it successfully)
        */
        TestClass testClassInst = new TestClass("Message", 1, 1);
        TestClass.InnerClass innerTestClassInst = testClassInst.new InnerClass(1);
        analyzer.analyze(innerTestClassInst);
        printEmptyLines(2);

        System.out.println("*** 'NestedStaticClass' analysis...\n");
        analyzer.analyze(TestClass.NestedClass.class);
        printEmptyLines(2);

        System.out.println("*** java.lang.Integer class analysis...\n");
        Integer integerVal = new Integer(20);
        analyzer.analyze(integerVal);
        printEmptyLines(2);

        System.out.println("*** java.io.PrintStream class analysis...");
        PrintStream printStream = System.out;
        analyzer.analyze(printStream);
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }
}
