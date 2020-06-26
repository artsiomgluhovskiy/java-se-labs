package org.art.java_core.nio.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.art.java_core.io.net.NetConstants.HOST;
import static org.art.java_core.io.net.NetConstants.TCP_PORT;

public class SimpleNioServer {

    private static final Logger log = LogManager.getLogger(SimpleNioServer.class);

    public void start() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(HOST, TCP_PORT));
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                pool.submit(() -> NetUtils.process(socketChannel));
            }
        } catch (IOException e) {
            log.error("Exception while server launching!", e);
        } finally {
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleNioServer server = new SimpleNioServer();
        server.start();
    }
}
