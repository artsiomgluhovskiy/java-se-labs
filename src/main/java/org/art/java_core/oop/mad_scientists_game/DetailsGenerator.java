package org.art.java_core.oop.mad_scientists_game;

import java.util.Random;

/**
 * Robot details factory (runs in a separate thread).
 * Provides a dump with details.
 */
public class DetailsGenerator extends Thread {

    //The default number of details generating for the first night
    private static final int DEFAULT_DETAILS_NUMBER = 20;

    private final GameContext context;
    private final Dump dump;

    private Details[] factoryDetails = Details.values();

    private Random random = new Random(System.currentTimeMillis());

    public DetailsGenerator(Dump dump, GameContext context) {
        this.dump = dump;
        this.context = context;
    }

    @Override
    public void run() {
        System.out.println("\"Details generator\" is running...");
        int detailsNumber;
        Details detail;
        genDefaultDetails();
        while (context.isRunning()) {
            if (!dump.isVisitedByGener()) {
                detailsNumber = random.nextInt(4) + 1;
                System.out.printf("\"Details generator\" wants to send %d details.%n", detailsNumber);
                for (int i = 0; i < detailsNumber; i++) {
                    detail = generateRandomDetail();
                    dump.pushDetail(detail);
                    System.out.printf("\"Details generator\" has sent the detail: %s.%n", detail.name());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dump.setVisitedByGener(true);
            }
        }
    }

    private Details generateRandomDetail() {
        int rndNum = random.nextInt(9);
        Details detail = factoryDetails[rndNum];
        return detail;
    }

    private void genDefaultDetails() {
        for (int i = 0; i < DEFAULT_DETAILS_NUMBER; i++) {
            dump.pushDetail(generateRandomDetail());
        }
        dump.setVisitedByGener(true);
        System.out.println("Default \"first night\" details have generated!!!");
    }
}
