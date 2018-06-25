package org.art.java_core.reflection_api.test_runner;

import org.art.java_core.reflection_api.test_runner.annotations.CustomExceptionTest;
import org.art.java_core.reflection_api.test_runner.annotations.CustomTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSample {

    @CustomTest
    public static void test1() {
        //Test should pass
        Set<String> map = new HashSet<>();
        map.add("String 1");
        map.add("String 2");
        map.add("String 3");
        if (map.size() != 3) {
            throw new AssertionError("Expected map size is: 3, but actual is: " + map.size());
        }
    }

    @CustomTest
    public static void test2() {
        //Test should fail
        Set<String> map = new HashSet<>();
        map.add("String 1");
        map.add("String 2");
        if (map.size() != 3) {
            throw new AssertionError("Expected map size is: 3, but actual is: " + map.size());
        }
    }

    @CustomExceptionTest(ArithmeticException.class)
    public static void test3() {
        //Test should pass
        int i = 0;
        i = i / i;
    }

    @CustomExceptionTest(ArithmeticException.class)
    public static void test4() {
        //Test should fail (wrong exception)
        int[] a = new int[0];
        int i = a[1];
    }

    @CustomExceptionTest(ArithmeticException.class)
    public static void test5() {
        //Test should fail (no exception)
    }

    @CustomExceptionTest({ IndexOutOfBoundsException.class,
    NullPointerException.class })
    public static void test6() {
        List<String> list = new ArrayList<>();

        //The spec permits this method to throw either
        //IndexOutOfBoundsException or NPE
        list.addAll(5, null);
    }

    public static void main(String[] args) {
        //Tests running
        TestRunner.runTests(TestSample.class);
    }
}
