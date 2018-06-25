package org.art.java_core.io.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

import static org.art.java_core.io.net.NetConstants.DEFAULT_BUFFER_SIZE;

/**
 * Server helper class implementation.
 * A part of the server that is responsible
 * for message processing coming from a client.
 */
public class ServerHelper {

    private static final Logger LOGGER = LogManager.getLogger(ServerHelper.class);

    public static void process(Socket socket) {
        LOGGER.debug("Socket processing: {}", socket);
        try (
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()
        ) {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while (in.read(buffer) != -1) {
                buffer = transmogrify(buffer);
                out.write(buffer);
                buffer = clearBuffer(buffer);
            }
        } catch (SocketException e) {
            LOGGER.error("Socket is not accessible. The most probably the socket was closed!");
        } catch (IOException e) {
            LOGGER.error("IOException was thrown during the socket processing!", e);
        }
    }

    private static byte[] transmogrify(byte[] data) {
        Objects.requireNonNull(data);
        for (int i = 0; i < data.length; i++) {
            if (Character.isLetter(data[i])) {
                data[i] = (byte) (data[i] ^ ' ');
            }
        }
        return data;
    }

    public static byte[] clearBuffer(byte[] data) {
        byte[] newBuffer;
        if (data != null) {
            newBuffer = new byte[data.length];
        } else {
            newBuffer = new byte[DEFAULT_BUFFER_SIZE];
        }
        return newBuffer;
    }
}
