package org.art.java_core.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class BasicSocketChannelTest {

    private static final Logger LOG = LogManager.getLogger(BasicSocketChannelTest.class);

    private static void blockingSocketChannelTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        boolean connectionEstablished = socketChannel.connect(new InetSocketAddress("onliner.by", 443));
        LOG.debug("Connection established: {}", connectionEstablished);
    }

    private static void nonBlockingSocketChannelTest() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        boolean connectionEstablished = socketChannel.connect(new InetSocketAddress("onliner.by", 443));
        LOG.debug("After 'connect()' method.");
        LOG.debug("Connection established: {}", connectionEstablished);
        while (!socketChannel.finishConnect()) {
            Thread.sleep(10);
        }
        LOG.debug("Connection established: true");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        blockingSocketChannelTest();
        nonBlockingSocketChannelTest();
    }
}
