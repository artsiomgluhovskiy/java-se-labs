package org.art.java_core.nio.net;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple Non-blocking Multithreaded Server Implementation
 */
public class NonblockingMultiThreadedServer extends BaseNonblockingServer {

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public NonblockingMultiThreadedServer(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
    }

    @Override
    protected void dataProcessing(ByteBuffer buffer, Map<SocketChannel, Queue<ByteBuffer>> pendingData,
                                  SocketChannel socket, SelectionKey key) throws ClosedChannelException {
        pool.submit(() -> {
            buffer.flip();
            ByteBuffer newBuffer;
            newBuffer = NetUtils.transmogrify(buffer);
            pendingData.get(socket).add(newBuffer);
            socket.register(key.selector(), SelectionKey.OP_WRITE);
            key.selector().wakeup();
            return null;
        });
    }

    public static void main(String[] args) throws InterruptedException {
        NonblockingMultiThreadedServer server = new NonblockingMultiThreadedServer(new ConcurrentHashMap<>());
        server.start();
    }
}

