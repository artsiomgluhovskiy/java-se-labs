package org.art.java_core.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class BasicSocketChannelTest {

    private static final Logger log = LogManager.getLogger(BasicSocketChannelTest.class);

    private static void blockingSocketChannelTest() throws IOException {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            boolean connectionEstablished = socketChannel.connect(new InetSocketAddress("onliner.by", 443));
            log.debug("Connection established: {}", connectionEstablished);
        }
    }

    private static void nonBlockingSocketChannelTest() throws Exception {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);
            boolean connectionEstablished = socketChannel.connect(new InetSocketAddress("onliner.by", 443));
            log.debug("After 'connect()' method.");
            log.debug("Connection established: {}", connectionEstablished);
            while (!socketChannel.finishConnect()) {
                Thread.sleep(10);
            }
            log.debug("Connection established: true");
        }
    }

    public static void main(String[] args) throws Exception {
        blockingSocketChannelTest();
        nonBlockingSocketChannelTest();
    }
}
