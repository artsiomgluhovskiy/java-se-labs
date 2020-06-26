package org.art.java_core.nio.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.art.java_core.io.net.NetConstants.HOST;
import static org.art.java_core.io.net.NetConstants.TCP_PORT;

public abstract class BaseNonblockingServer implements Server {

    private static final Logger log = LogManager.getLogger(NonblockingSingleThreadedServer.class);

    private Map<SocketChannel, Queue<ByteBuffer>> pendingData;
    private Selector selector;

    public BaseNonblockingServer(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        this.pendingData = pendingData;
    }

    public void start() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(HOST, TCP_PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                for (Iterator<SelectionKey> keysIter = selector.selectedKeys().iterator(); keysIter.hasNext(); ) {
                    SelectionKey key = keysIter.next();
                    keysIter.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            //Someone connected to our ServerSocketChannel
                            accept(key);
                        } else if (key.isReadable()) {
                            read(key);
                        } else if (key.isWritable()) {
                            write(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Exception while server launching!", e);
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Queue<ByteBuffer> queue = pendingData.get(sc);
        ByteBuffer buf;
        while ((buf = queue.peek()) != null) {
            sc.write(buf);
            if (!buf.hasRemaining()) {
                queue.poll();
            } else {
                return;
            }
        }
        sc.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socket = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(32);
        int read = socket.read(buffer);
        if (read == -1) {
            pendingData.remove(socket);
            return;
        }
        dataProcessing(buffer, pendingData, socket, key);
    }

    protected abstract void dataProcessing(ByteBuffer buffer, Map<SocketChannel, Queue<ByteBuffer>> pendingData,
                                           SocketChannel socket, SelectionKey key) throws ClosedChannelException;

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();    //non-blocking, never null
        sc.configureBlocking(false);
        log.debug("Connection from {}", sc);
        sc.register(key.selector(), SelectionKey.OP_READ);
        pendingData.put(sc, new ConcurrentLinkedQueue<>());
    }
}
