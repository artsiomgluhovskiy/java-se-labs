package org.art.java_core.io.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.art.java_core.io.net.NetConstants.TCP_PORT;

/**
 * Single Threaded Simple Echo-Server Implementation.
 */
public class SimpleServer {

    private static final Logger LOGGER = LogManager.getLogger(SimpleServer.class);

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ServerHelper.process(socket);
            }
        } catch (IOException e) {
            LOGGER.error("IOException was thrown during the server running!", e);
        }
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.start();
    }
}
