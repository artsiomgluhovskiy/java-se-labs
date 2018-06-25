package org.art.java_core.nio.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Simple Non-blocking Server Implementation (one thread maintains all server operations).
 */
public class NonblockingSingleThreadedServer extends BaseNonblockingServer {

    private static final Logger LOGGER = LogManager.getLogger(NonblockingSingleThreadedServer.class);

    public NonblockingSingleThreadedServer(Map<SocketChannel, Queue<ByteBuffer>> pendingData) {
        super(pendingData);
    }

    @Override
    protected void dataProcessing(ByteBuffer buffer, Map<SocketChannel, Queue<ByteBuffer>> pendingData,
                                  SocketChannel socket, SelectionKey key) throws ClosedChannelException {
        buffer.flip();
        ByteBuffer newBuffer;
        newBuffer = NetUtils.transmogrify(buffer);
        pendingData.get(socket).add(newBuffer);
        socket.register(key.selector(), SelectionKey.OP_WRITE);
    }

    public static void main(String[] args) throws InterruptedException {
        NonblockingSingleThreadedServer server = new NonblockingSingleThreadedServer(new HashMap<>());
        server.start();
    }
}
