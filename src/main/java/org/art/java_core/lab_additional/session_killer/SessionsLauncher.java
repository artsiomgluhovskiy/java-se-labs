package org.art.java_core.lab_additional.session_killer;

public class SessionsLauncher {

    public static void main(String[] args) throws InterruptedException {

        CustomSessionRepository sessions = new CustomSessionRepository();
        sessions.putSession("User_1", new CustomSession(System.currentTimeMillis()));
        Thread.sleep(2 * 1000);
        sessions.putSession("User_2", new CustomSession(System.currentTimeMillis()));
        Thread.sleep(2 * 1000);
        sessions.putSession("User_3", new CustomSession(System.currentTimeMillis()));
        sessions.activateSession("User_1");
    }
}
