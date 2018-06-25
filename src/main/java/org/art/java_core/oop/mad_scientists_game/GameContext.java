package org.art.java_core.oop.mad_scientists_game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 'Mad scientists' game context.
 *  Game actors:
 *  - scientist 1 (separate thread);
 *  - scientist 2 (separate thread)
 *  - details factory (separate thread).
 *  - dump of details (bounded blocking buffer).
 *  The scientist which manufactures the largest number of
 *  robots (during N nights) wins.
 *
 *  Design of the game is based on the 'consumer/producer' pattern.
 */
public class GameContext implements Runnable {

    private static final Logger LOG = LogManager.getLogger(GameContext.class);

    private static final int DEFAULT_NIGHTS_NUMBER = 100;

    private volatile boolean isRunning = true;

    private void printResults(MadScientist sc1, MadScientist sc2) {
        System.out.printf("%s --> Amount of robots: %d%n", sc1.getScName(), sc1.defineRobotsAmount());
        System.out.printf("%s --> Amount of robots: %d%n", sc2.getScName(), sc2.defineRobotsAmount());
        if (sc1.defineRobotsAmount() > sc2.defineRobotsAmount()) {
            System.out.printf("%s is a winner!!!%n", sc1.getScName());
        } else if (sc1.defineRobotsAmount() < sc2.defineRobotsAmount()) {
            System.out.printf("%s is a winner!!!%n", sc2.getScName());
        } else {
            System.out.println("It's a draw!!!");
        }
    }

    @Override
    public void run() {
        Dump dump = new Dump();

        //Actors initialization and launching
        DetailsGenerator generator = new DetailsGenerator(dump, this);
        MadScientist scientist1 = new MadScientist("Mad scientist 1", dump, this);
        scientist1.preparingForGame();
        MadScientist scientist2 = new MadScientist("Mad scientist 2", dump, this);
        scientist2.preparingForGame();
        generator.start();
        scientist1.start();
        scientist2.start();

        //Game launching
        try {
            for (int i = 0; i < DEFAULT_NIGHTS_NUMBER; i++) {
                System.out.printf("*** Night №%d ***%n", i + 1);
                dump.resetDump();
                Thread.sleep(100);
                Thread.yield();
                System.out.println();
            }
            isRunning = false;
            scientist1.join();
            scientist2.join();
            generator.join();
        } catch (InterruptedException e) {
            LOG.error("The process has been terminated...", e);
        }

        //Results of the game
        System.out.printf("Details collection of \"%s\"%n%s%n", scientist1.getScName(), scientist1.getDetailsMap());
        System.out.printf("Details collection of \"%s\"%n%s%n", scientist2.getScName(), scientist2.getDetailsMap());
        printResults(scientist1, scientist2);

        //End of the game
        System.out.println("*** End of the game! ***");
    }

    public boolean isRunning() {
        return isRunning;
    }

    public static void main(String[] args) throws InterruptedException {
        //Game launching
        new Thread(new GameContext()).start();
    }
}
