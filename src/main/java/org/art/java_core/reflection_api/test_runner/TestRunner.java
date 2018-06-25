package org.art.java_core.reflection_api.test_runner;

import org.art.java_core.reflection_api.test_runner.annotations.CustomExceptionTest;
import org.art.java_core.reflection_api.test_runner.annotations.CustomTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Test methods processor.
 */
public class TestRunner {

    public static void runTests(Class clazz) {
        int tests = 0;
        int passed = 0;
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(CustomTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    System.out.println(m.getName() + " failed: " + exc);
                } catch (Exception e) {
                    System.out.println("Invalid @Test: " + m.getName());
                }
            } else if (m.isAnnotationPresent(CustomExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (InvocationTargetException e) {
                    Throwable exc = e.getCause();
                    int oldPassed = passed;
                    Class<? extends  Exception>[] excTypes = m.getAnnotation(CustomExceptionTest.class).value();
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s %n", m.getName(), exc);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid @Test: " + m.getName());
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
