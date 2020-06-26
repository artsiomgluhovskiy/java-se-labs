package org.art.java_core.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class BasicFileChannelTest {

    private static final Logger log = LogManager.getLogger(BasicFileChannelTest.class);

    private static final String BASIC_FILE_PATH = "files/nio/basic-channel-test.txt";
    private static final String IN_FILE = "files/nio/from-file.txt";
    private static final String IN_FILE_1 = "files/nio/from-file1.txt";
    private static final String OUT_FILE = "files/nio/to-file.txt";
    private static final String OUT_FILE_1 = "files/nio/to-file1.txt";

    private static void readFromFileChannel() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(getFile(BASIC_FILE_PATH), "rw");
             FileChannel inChannel = file.getChannel()) {
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                log.debug("Read {}", bytesRead);
                buf.flip();
                while (buf.hasRemaining()) {
                    log.debug((char) buf.get());
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        }
    }

    private static void transferFromChannel() throws Exception {
        try (RandomAccessFile fromFile = new RandomAccessFile(getFile(IN_FILE), "rw");
             FileChannel fromChannel = fromFile.getChannel();
             RandomAccessFile toFile = new RandomAccessFile(getFile(OUT_FILE), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            long position = 0;
            long count = fromChannel.size();
            toChannel.transferFrom(fromChannel, position, count);
        }
    }

    private static void transferToChannel() throws Exception {
        try (RandomAccessFile fromFile = new RandomAccessFile(getFile(IN_FILE_1), "rw");
             FileChannel fromChannel = fromFile.getChannel();
             RandomAccessFile toFile = new RandomAccessFile(getFile(OUT_FILE_1), "rw");
             FileChannel toChannel = toFile.getChannel()) {
            long position = 0;
            long count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
        }
    }

    private static File getFile(String path) throws Exception {
        URL fileURL = BasicFileChannelTest.class.getClassLoader().getResource(path);
        return Paths.get(fileURL.toURI()).toFile();
    }

    public static void main(String[] args) throws Exception {
        readFromFileChannel();
        transferFromChannel();
        transferToChannel();
    }
}
