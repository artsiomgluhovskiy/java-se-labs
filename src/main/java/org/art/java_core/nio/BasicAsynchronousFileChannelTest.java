package org.art.java_core.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class BasicAsynchronousFileChannelTest {

    private static final Logger LOGGER = LogManager.getLogger(BasicAsynchronousFileChannelTest.class);

    private static final String FILE_PATH = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\nio\\large-file.txt";
    private static final String DUMMY_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n";
    private static final int LINES = 5000;

    private static void generateLargeFile() {
        File file = new File(FILE_PATH);
        try (Writer out = new BufferedWriter(new FileWriter(file))) {
            char[] buffer = DUMMY_STRING.toCharArray();
            for (int i = 0; i < LINES; i++) {
                out.write(buffer);
            }
            out.flush();
            LOGGER.debug("File was successfully generated!");
        } catch (IOException e) {
            LOGGER.error("Exception while file generating!", e);
        }
    }

    private static void blockingRead() {
        char[] buffer = new char[DUMMY_STRING.toCharArray().length * LINES];
        File file = new File(FILE_PATH);
        try (Reader in = new BufferedReader(new FileReader(file))) {
            LOGGER.debug("Before blocking reading...");
            in.read(buffer);        //blocking operation
            LOGGER.debug("File was successfully read! Buffer size: {}", buffer.length);
        } catch (IOException e) {
            LOGGER.error("Exception while file reading!", e);
        }
    }

    private static void nonBlockingRead() {
        Path filePath = Paths.get(FILE_PATH);
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(DUMMY_STRING.getBytes().length * LINES);
            LOGGER.debug("Before non-blocking reading...");
            Future<Integer> operation = fileChannel.read(buffer, 0);        //non-blocking operation
            while (!operation.isDone()) {
                LOGGER.debug("After 'read' method invocation. File was read: {}", operation.isDone());
            }
            LOGGER.debug("File was successfully read! {} bytes were read.", operation.get());
            buffer.flip();
            //ByteBuffer is ready to be read...
        } catch (Exception e) {
            LOGGER.error("Exception while file reading!", e);
        }
    }

    public static void main(String[] args) {

        generateLargeFile();

        //Blocking reading from the file
        blockingRead();

        //Non-blocking reading from the file
        nonBlockingRead();
    }
}
