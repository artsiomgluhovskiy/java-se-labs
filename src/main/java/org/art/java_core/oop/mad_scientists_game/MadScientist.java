package org.art.java_core.oop.mad_scientists_game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Scientist actor implementation (runs in a separate thread).
 */
public class MadScientist extends Thread {

    private static final Logger LOG = LogManager.getLogger(MadScientist.class);

    private String name;

    private Map<Details, Integer> detailsMap = new HashMap<>();

    private final GameContext context;
    private final Dump dump;

    private Random random = new Random(System.currentTimeMillis() + 34);

    public MadScientist(String name, Dump dump, GameContext context) {
        this.name = name;
        this.dump = dump;
        this.context = context;
    }

    @Override
    public void run() {
        System.out.printf("\"%s\" is running!%n", name);
        try {
            while (context.isRunning()) {
                int takenDetails = 0;
                if (!dump.isVisitedBySc()) {
                    int wantedDetails = random.nextInt(4) + 1;
                    System.out.printf("\"%s\" wants to take %d detail(s).%n", name, wantedDetails);
                    for (int i = 0; i < wantedDetails; i++) {
                        Details det = dump.popDetail();
                        if (det != null) {
                            detailsMap.put(det, detailsMap.get(det) + 1);
                            takenDetails++;
                            System.out.printf("\"%s\" has taken: %s.%n", name, det.name());
                        }
                        Thread.sleep(1);
                    }
                    if (takenDetails == 0) {
                        System.out.printf("\"%s\" has taken nothing (the dump was empty)! =(%n", name);
                    } else if (takenDetails != wantedDetails) {
                        System.out.printf("\"%s\" has taken only (the dump was not full enough) %d detail(s).%n", name, takenDetails);
                    }
                    dump.setVisitedBySc(true);
                    sleep(1);
                }
            }
        } catch (InterruptedException e) {
            LOG.error("The process (Mad Scientist - {}) has been terminated!", name, e);
        }
    }

    public void preparingForGame() {
        Details[] factoryOfDetails = Details.values();
        for (int i = 0; i < factoryOfDetails.length; i++) {
            detailsMap.put(factoryOfDetails[i], 0);
        }
    }

    public Map<Details, Integer> getDetailsMap() {
        return new HashMap<>(detailsMap);
    }

    public int defineRobotsAmount() {
        Collection<Integer> values = detailsMap.values();
        return values.stream()
                .min(Integer::compareTo)
                .orElse(0);
    }

    public String getScName() {
        return name;
    }
}
