package org.art.java_core.strings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * String concatenation speed tests.
 * Test cases:
 * - simple string addition ('string 1' + 'string 2');
 * - string concatenation (String.concat() method);
 * - concatenation with StringBuffer;
 * - concatenation with StringBuilder;
 * - concatenation with StringJoiner.
 */
public class StringTest {

    private static final Logger LOG = LogManager.getLogger(StringTest.class);

    private static final String INIT_STRING_VALUE = "Hello";
    private static final String R_N = "\n";

    private static final int DEFAULT_OPS_NUMBER = 20_000;
    private static final int DEFAULT_TESTS_NUMBER = 8;

    private abstract static class TestBase {

        private String testName;
        private int testOrder;
        private long sumResults;

        public TestBase(String testName, int testOrder) {
            this.testName = testName;
            this.testOrder = testOrder;
        }

        public void runTest() {
            long start = System.nanoTime();
            test();
            long elapsedTime = System.nanoTime() - start;
            addResults(elapsedTime);
        }

        protected abstract void test();

        public String getTestName() {
            return testName;
        }

        public int getTestOrder() {
            return testOrder;
        }

        public String getResultsString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Test: ").append(testName).append(R_N)
                    .append("Test order: ").append(testOrder).append(R_N)
                    .append("Number of tests: ").append(DEFAULT_TESTS_NUMBER).append(R_N)
                    .append("Number of concatenations: ").append(DEFAULT_OPS_NUMBER).append(R_N)
                    .append("Average elapsed time (in ms): ").append(getAverageResult() / 1.0e6).append(R_N);
            return sb.toString();
        }

        public void addResults(long elapsedTime) {
            System.out.println(testName + " " + elapsedTime);
            this.sumResults += elapsedTime;
        }

        public float getAverageResult() {
            return (float) (this.sumResults / DEFAULT_TESTS_NUMBER);
        }

        @Override
        public String toString() {
            return "TestBase{" +
                    "testName='" + testName + '\'' +
                    ", testOrder=" + testOrder +
                    '}';
        }
    }

    private static TestBase[] tests = {

            new TestBase("Strings addition (+)", 1) {

                @Override
                public void test() {
                    String str = INIT_STRING_VALUE;
                    for (int i = 0; i < DEFAULT_OPS_NUMBER; i++) {
                        str = str + i;
                    }
                }
            },

            new TestBase("Strings concatenation 'concat()' method", 2) {

                @Override
                public void test() {
                    String str = INIT_STRING_VALUE;
                    for (int i = 0; i < DEFAULT_OPS_NUMBER; i++) {
                        str = str.concat(String.valueOf(i));
                    }
                }
            },

            new TestBase("Concatenation with StringBuffer", 3) {

                @Override
                public void test() {
                    StringBuffer sb = new StringBuffer(INIT_STRING_VALUE);
                    for (int i = 0; i < DEFAULT_OPS_NUMBER; i++) {
                        sb.append(i);
                    }
                }
            },

            new TestBase("Concatenation with StringBuilder", 4) {

                @Override
                public void test() {
                    StringBuilder sb = new StringBuilder(INIT_STRING_VALUE);
                    for (int i = 0; i < DEFAULT_OPS_NUMBER; i++) {
                        sb.append(i);
                    }
                }
            },

            new TestBase("Concatenation with StringJoiner (based on StringBuilder)", 5) {

                @Override
                public void test() {
                    StringJoiner sj = new StringJoiner(INIT_STRING_VALUE);
                    for (int i = 0; i < DEFAULT_OPS_NUMBER; i++) {
                        sj.add(String.valueOf(i));
                    }
                }
            }
    };

    public static void main(String[] args) {

        //Tests running (in accordance with the test order property)
        Comparator<TestBase> testPriorityComparator = Comparator.comparingInt(TestBase::getTestOrder);
        Set<TestBase> sortedTests = new TreeSet<>(testPriorityComparator);
        sortedTests.addAll(Arrays.asList(tests));

        IntStream.range(0, DEFAULT_TESTS_NUMBER)
                .forEach((testNumber) -> sortedTests.forEach(TestBase::runTest));

        List<String> results = sortedTests.stream()
                .map(TestBase::getResultsString)
                .collect(Collectors.toList());

        System.out.println("TEST RESULTS");
        results.forEach(System.out::println);
    }
}
