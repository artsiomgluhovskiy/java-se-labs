package org.art.java_core.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Input/output file operations speed tests.
 * Test cases:
 * - IO read/write test with character stream WITHOUT buffering;
 * - IO read/write test with character stream WITH buffering;
 * - IO read/write test with byte stream WITHOUT buffering;
 * - IO read/write test with byte stream WITH buffering;
 * - NIO read/write test with file-channel/byte-buffer and File Memory Mapping feature.
 */
public class FileTest {

    private static final Logger LOG = LogManager.getLogger(FileTest.class);

    private static final String IN_CHAR_FILE_PATH = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\io\\in-char-file.txt";
    private static final String IN_BYTE_FILE_PATH = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\io\\in-byte-file.txt";
    private static final String OUT_FILE_PATH = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\io\\out-file.txt";

    private static final long DEFAULT_FILE_SIZE = 0x501400 * 5;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    private abstract static class TestBase {

        private String testName;
        private int testOrder;

        public TestBase(String testName, int testOrder) {
            this.testName = testName;
            this.testOrder = testOrder;
        }

        public void runTest() {
            System.out.print("Test: " + testName + " " + testOrder);
            long start = System.nanoTime();
            test();
            long elapsedTime = System.nanoTime() - start;
            System.out.format(" Elapsed time: %.2f ms.%n", elapsedTime / 1.0e6);
        }

        protected abstract void test();

        public String getTestName() {
            return testName;
        }

        public int getTestOrder() {
            return testOrder;
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

            new TestBase("IO Character stream WITHOUT buffering", 5) {

                @Override
                public void test() {
                    int ch;
                    try (
                        FileReader in = new FileReader(IN_CHAR_FILE_PATH);
                        FileWriter out = new FileWriter(OUT_FILE_PATH, false)
                    ) {
                        while ((ch = in.read()) != -1) {
                            out.write(ch);
                        }
                    } catch (IOException e) {
                        LOG.error("IOException has been caught in test {}!", getTestName(), e);
                    }
                }
            },

            new TestBase("IO Character stream WITH buffering", 4) {

                @Override
                public void test() {
                    String str;
                    try (
                        BufferedReader in = new BufferedReader(new FileReader(IN_CHAR_FILE_PATH), DEFAULT_BUFFER_SIZE);
                        BufferedWriter out = new BufferedWriter(new FileWriter(OUT_FILE_PATH, false), DEFAULT_BUFFER_SIZE)
                    ) {
                        while ((str = in.readLine()) != null) {
                            out.write(str + "\n");
                        }
                    } catch (IOException e) {
                        LOG.error("IOException has been caught in test {}!", getTestName(), e);
                    }
                }
            },

            new TestBase("IO Byte stream WITHOUT buffering", 3) {

                @Override
                public void test() {
                    int b;
                    try (
                        FileInputStream in = new FileInputStream(IN_BYTE_FILE_PATH);
                        FileOutputStream out = new FileOutputStream(OUT_FILE_PATH)
                    ) {
                        while ((b = in.read()) != -1) {
                            out.write(b);
                        }
                    } catch (IOException e) {
                        LOG.error("IOException has been caught in test {}!", getTestName(), e);
                    }
                }
            },

            new TestBase("IO Byte stream WITH buffering", 2) {

                @Override
                public void test() {
                    int ch;
                    try (
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(IN_BYTE_FILE_PATH), DEFAULT_BUFFER_SIZE);
                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(OUT_FILE_PATH), DEFAULT_BUFFER_SIZE)
                    ) {
                        while ((ch = in.read()) != -1) {
                            out.write(ch);
                        }
                    } catch (IOException e) {
                        LOG.error("IOException has been caught in test {}!", getTestName(), e);
                    }
                }
            },

            new TestBase("NIO Byte stream with memory mapping feature", 1) {

                @Override
                public void test() {
                    try (
                        FileChannel in = new RandomAccessFile(new File(IN_BYTE_FILE_PATH), "rw").getChannel();
                        FileChannel out = new RandomAccessFile(new File(OUT_FILE_PATH), "rw").getChannel()
                    ) {
                        ByteBuffer bb = in.map(FileChannel.MapMode.READ_WRITE, 0, in.size());
                        while (bb.hasRemaining()) {
                            bb.get();
                        }
                        bb.flip();
                        out.write(bb);
                    } catch (IOException e) {
                        LOG.error("IOException has been caught in test {}!", getTestName(), e);
                    }
                }
            },
    };

    public static File generateCharFile(String fileName, long fileSize) {
        int k = 0;
        int charsNumber = 40;
        File file = new File(fileName);
        try (FileWriter fw = new FileWriter(file)) {
            while (k < fileSize / (charsNumber * 2)) {
                for (int i = 0; i < charsNumber - 1; i++) {
                    fw.write('А' + i);
                }
                fw.write(" \n");
                k++;
            }
        } catch (IOException e) {
            LOG.info("IOException has been caught during file generation!", e);
        }
        return file;
    }

    public static File generateByteFile(String filename, long fileSize) {
        byte ch = 1;
        int k = 0;
        int bytesNumber = 80;
        File file = new File(filename);
        try (FileOutputStream in = new FileOutputStream(file)) {
            while (k < fileSize / bytesNumber) {
                for (int i = 0; i < bytesNumber - 1; i++) {
                    ch += i;
                    in.write(ch);
                }
                in.write('\n');
                k++;
            }
        } catch (IOException e) {
            LOG.info("IOException has been caught during file generation!", e);
        }
        return file;
    }

    public static void main(String[] args) {

        System.out.println("Character file generation...");
        File file = generateCharFile(IN_CHAR_FILE_PATH, DEFAULT_FILE_SIZE);
        System.out.println("The character file has been generated!");
        System.out.println("File length: " + file.length() / (1024 * 1024) + " MB.\n");

        System.out.println("Byte file generation...");
        file = generateByteFile(IN_BYTE_FILE_PATH, DEFAULT_FILE_SIZE / 5);
        System.out.println("The byte file has been generated!");
        System.out.println("File length: " + file.length() / (1024 * 1024) + " MB.\n");

        //Tests running (in accordance with the test order property)
        Comparator<TestBase> testPriorityComparator = Comparator.comparingInt(TestBase::getTestOrder);
        Set<TestBase> sortedTests = new TreeSet<>(testPriorityComparator);
        sortedTests.addAll(Arrays.asList(tests));
        sortedTests.forEach(TestBase::runTest);
    }
}



