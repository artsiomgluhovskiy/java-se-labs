package org.art.java_core.lab_additional.session_killer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Simple session repository (context) impl. Contains the
 * collection of living session objects. By default
 * the session living time equals to 5 sec.
 * After time expiration the session is deactivated.
 * Note that session can be activated during the
 * living time so that the life cycle starts from the
 * beginning.
 */
public class CustomSessionRepository {

    private static final Logger LOG = LogManager.getLogger(CustomSessionRepository.class);

    private long startRepositoryTime;
    private static volatile String lastSessionName;
    private final Map<String, CustomSession> sessions = new ConcurrentHashMap<>();
    private SessionKiller killer;

    public CustomSessionRepository() {
        startRepositoryTime = System.currentTimeMillis();
        killer = new SessionKiller();
        killer.start();
    }

    public void putSession(String key, CustomSession session) {
        lastSessionName = key;
        killer.addTask(key, session);
        sessions.put(key, session);
        System.out.printf("Session %s has been added to the repository! Repository size: %d.%n", session, sessions.size());
    }

    public void removeSession(String sessionName) {
        sessions.remove(sessionName);
        System.out.printf("Session %s was killed! Current time: %d seconds from repository start.%n",
                sessionName, (System.currentTimeMillis() - startRepositoryTime) / 1000);
        System.out.println("Session repository size: " + sessions.size());
    }

    public void activateSession(String sessionName) {
        CustomSession session;
        if ((session = sessions.get(sessionName)) != null) {
            session.setDeathTime(session.getDeathTime() + 5 * 1000);
            session.setActivated(true);
            killer.addTask(sessionName, session);
        } else {
            System.out.println("Session was deleted!");
        }
    }

    private class SessionKiller extends Thread {

        private KillerTask bufTask;

        private LinkedBlockingDeque<KillerTask> taskList = new LinkedBlockingDeque<>();

        public synchronized void addTask(String key, CustomSession session) {
            long deathTimeOfPreviousTask;
            if (sessions.size() == 0) {
                taskList.addLast(new KillerTask(key, session.getDeathTime() - session.getBirthTime(), session.getDeathTime()));
                System.out.println("Task list size = " + taskList.size());
            } else {
                if (taskList.size() == 0) {
                    deathTimeOfPreviousTask = bufTask.getDeathTime();
                    bufTask = null;
                } else {
                    deathTimeOfPreviousTask = taskList.peekLast().getDeathTime();
                }
                taskList.addLast(new KillerTask(key, session.getDeathTime() - deathTimeOfPreviousTask, session.getDeathTime()));
            }
        }

        public void run() {
            System.out.println("Thread killer is running...");
            while (true) {
                try {
                    KillerTask task = taskList.takeFirst();
                    if (taskList.size() == 0) {
                        bufTask = task;
                    }
                    System.out.println("Killer took new task. The size of the task list is " + taskList.size());
                    long sleepingTime = task.getSleepTime();
                    String sessionName = task.getSessionName();
                    System.out.printf("Sleeping time = %d. Session name = %s.%n", sleepingTime, sessionName);
                    Thread.sleep(sleepingTime);
                    if (!sessions.get(sessionName).isActivated()) {
                        removeSession(sessionName);
                    } else {
                        sessions.get(sessionName).setActivated(false);
                    }
                } catch (InterruptedException e) {
                    LOG.error("InterruptedException has been caught!", e);
                }
            }
        }
    }
}
