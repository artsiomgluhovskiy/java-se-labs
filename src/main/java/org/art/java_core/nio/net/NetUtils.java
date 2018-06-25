package org.art.java_core.nio.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class NetUtils {

    private static final Logger LOGGER = LogManager.getLogger(NetUtils.class);

    public static void process(SocketChannel socketChannel) {
        LOGGER.debug("Connection from {}", socketChannel);
        try {
            ByteBuffer buffer = ByteBuffer.allocateDirect(32);
            while (socketChannel.read(buffer) != -1) {
                buffer = transmogrify(buffer);
                socketChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            LOGGER.error("Exception while socket processing!", e);
        }
    }

    public static ByteBuffer transmogrify(ByteBuffer data) {
        Objects.requireNonNull(data);
        for (int i = 0; i < data.limit(); i++) {
            data.put(i, (byte) (data.get(i) ^ ' '));
        }
        return data;
    }
}
