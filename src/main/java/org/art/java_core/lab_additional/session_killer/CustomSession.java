package org.art.java_core.lab_additional.session_killer;

/**
 * Simple custom session implementation.
 */
public class CustomSession {

    private long birthTime;     //in ms
    private long deathTime;     //in ms
    private boolean activated = false;

    //Default life time period of session is 5 sec
    public CustomSession(long birthTime) {
        this.birthTime = birthTime;
        this.deathTime = birthTime + 5 * 1000;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }

    public long getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "CustomSession{" +
                "birthTime=" + birthTime +
                ", deathTime=" + deathTime +
                '}';
    }
}
