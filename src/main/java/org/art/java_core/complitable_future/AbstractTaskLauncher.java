package org.art.java_core.complitable_future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ning.http.client.AsyncHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_core.complitable_future.http.*;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class AbstractTaskLauncher {

    protected final Logger LOGGER = LogManager.getLogger(this.getClass());

    protected final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory("Custom Thread"));

    protected final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private final Scanner SCANNER = new Scanner(System.in);

    //Note: 'Decorator' pattern implementation
    protected final StackOverflowClient client = new FallbackStubClient(
            new InjectErrorsWrapper(
                    new LoggingWrapper(
                            new ArtificialSleepWrapper(
                                    new StackOverflowClientImpl()
                            )
                    ), "php"
            )
    );

    private ThreadFactory threadFactory(String nameFormat) {
        return new ThreadFactoryBuilder().setNameFormat(nameFormat + "-%d").build();
    }

    protected int getTaskId() {
        System.out.println("Enter Task ID: <Integer value> , or enter '0' to exit:");
        return SCANNER.nextInt();
    }

    protected void shutDownAll() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        asyncHttpClient.close();
    }
}
