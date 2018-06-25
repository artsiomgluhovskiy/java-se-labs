package org.art.java_core.io.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.art.java_core.io.net.NetConstants.TCP_PORT;

/**
 * Multithreaded Simple Echo-Server implementation
 * with the ability to stop the server correctly
 * (threads are taken from a configured ThreadPool).
 */
public class SimpleExecutorServiceServer implements IServer {

    private static final Logger LOGGER = LogManager.getLogger(SimpleExecutorServiceServer.class);

    private static final String MAGIC_SERVER_KILL_WORD = "kill";

    private ExecutorService pool;
    private List<Socket> sockets = new ArrayList<>();
    private volatile boolean serverRunning;

    public SimpleExecutorServiceServer(ExecutorService pool) {
        this.pool = pool;
    }

    public void start() {
        ServerKiller killer = null;
        //Server launching
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            LOGGER.debug("Server is running...");
            serverRunning = true;

            //Server killer launching
            killer = new ServerKiller();
            killer.setTargetServerSocket(serverSocket);
            new Thread(killer).start();

            while (serverRunning) {
                Socket socket = serverSocket.accept();
                LOGGER.debug("Connection established. New socket was accepted...");
                sockets.add(socket);
                pool.submit(() -> ServerHelper.process(socket));
            }
        } catch (SocketException e) {
            LOGGER.error("Server Socket is not accessible. The most probably the server socket was closed!");
        } catch (IOException e) {
            LOGGER.error("IOException was thrown during the server running!", e);
        } finally {
            if (killer != null) {
                killer.killServer();
            }
        }
    }

    private class ServerKiller implements Runnable {

        volatile ServerSocket serverSocket;

        @Override
        public void run() {
            Scanner scanner;
            String command;
            do {
                System.out.println("Enter 'kill' command to stop the server:");
                scanner = new Scanner(System.in);
                command = scanner.nextLine();
            } while (!command.equals(MAGIC_SERVER_KILL_WORD));
            scanner.close();
            LOGGER.debug("Server running - false...");
            serverRunning = false;
            killServer();
        }

        void killServer() {
            try {
                if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                    LOGGER.debug("Thread pool shutdown and termination...");
                    pool.shutdown();
                    pool.awaitTermination(2, TimeUnit.SECONDS);
                }
                if (!serverSocket.isClosed()) {
                    LOGGER.debug("Server socket closing...");
                    serverSocket.close();
                }
                if (sockets != null && !sockets.isEmpty()) {
                    LOGGER.debug("Sockets closing...");
                    sockets.stream()
                            .filter(socket -> !socket.isClosed())
                            .forEach(socket -> {
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    LOGGER.error("IOException was thrown during socket closing!", e);
                                }
                            });
                }
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException was thrown during tasks termination!", e);
            } catch (IOException e) {
                LOGGER.error("IOException was thrown during server socket closing!", e);
            }
        }

        public void setTargetServerSocket(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = new ThreadPoolExecutor(
                0, 500,
                60, TimeUnit.SECONDS, new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        SimpleExecutorServiceServer server = new SimpleExecutorServiceServer(pool);
        server.start();
    }
}
