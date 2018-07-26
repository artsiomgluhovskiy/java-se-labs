package org.art.java_core.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BasicFileChannelTest {

    private static final Logger LOGGER = LogManager.getLogger(BasicFileChannelTest.class);

    private static final String BASIC_FILE_PATH = ".\\src\\main\\resources\\files\\nio\\basic-channel-test.txt";
    private static final String IN_FILE = ".\\src\\main\\resources\\files\\nio\\from-file.txt";
    private static final String IN_FILE_1 = ".\\src\\main\\resources\\files\\nio\\from-file1.txt";
    private static final String OUT_FILE = ".\\src\\main\\resources\\files\\nio\\to-file.txt";
    private static final String OUT_FILE_1 = ".\\src\\main\\resources\\files\\nio\\to-file1.txt";

    private static void readFromFileChannel() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(BASIC_FILE_PATH, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            LOGGER.debug("Read {}", bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                LOGGER.debug((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    private static void transferFromChannel() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(IN_FILE, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(OUT_FILE, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    private static void transferToChannel() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(IN_FILE_1, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(OUT_FILE_1, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }

    public static void main(String[] args) throws IOException {
        readFromFileChannel();
        transferFromChannel();
        transferToChannel();
    }
}
