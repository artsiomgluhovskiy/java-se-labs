package org.art.java_core.lab_additional.session_killer;

public class KillerTask {

    private String sessionName;
    private long sleepTime;
    private long deathTime;
    private boolean activated = false;

    public KillerTask(String sessionName, long sleepTime, long deathTime) {
        this.sessionName = sessionName;
        this.sleepTime = sleepTime;
        this.deathTime = deathTime;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
