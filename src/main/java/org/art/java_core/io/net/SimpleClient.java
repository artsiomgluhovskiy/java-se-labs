package org.art.java_core.io.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static org.art.java_core.io.net.NetConstants.*;
import static org.art.java_core.io.net.ServerHelper.clearBuffer;

/**
 * Simple net client implementation.
 */
public class SimpleClient {

    private static final Logger LOGGER = LogManager.getLogger(SimpleClient.class);

    public static final String MAGIC_EXIT_WORD = "exit";

    public void start() {
        LOGGER.debug("Client is running...");
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        try (Socket socket = new Socket(HOST, TCP_PORT)) {
            try (OutputStream out = socket.getOutputStream();
                 InputStream in = socket.getInputStream();
                 Scanner scanner = new Scanner(System.in)) {
                String message;
                do {
                    message = getMessage(scanner);
                    byte[] bytes = message.getBytes();
                    out.write(bytes);
                    in.read(buffer);
                    String response = new String(buffer);
                    System.out.println("Response from server: " + response);
                    buffer = clearBuffer(buffer);
                } while (!message.equalsIgnoreCase(MAGIC_EXIT_WORD));
            }
        } catch (SocketException e) {
            LOGGER.error("Connection is broken. The most probably the socket was closed!");
        } catch (IOException e) {
            LOGGER.error("IOException was thrown during socket processing!", e);
        }
    }

    private String getMessage(Scanner scanner) {
        System.out.println("Enter the message you want to send to the server or enter 'exit' to exit the client:");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        SimpleClient client = new SimpleClient();
        client.start();
    }
}
