package org.art.java_core.oop.mad_scientists_game;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Robot details dump (bounded blocking buffer).
 */
public class Dump {

    private volatile boolean visitedBySc1 = false;
    private volatile boolean visitedByGener = false;

    private Deque<Details> detailsDump = new ConcurrentLinkedDeque<>();

    public void resetDump() {
        this.visitedBySc1 = false;
        this.visitedByGener = false;
    }

    public synchronized void pushDetail(Details detail) {
        detailsDump.push(detail);
    }

    public synchronized Details popDetail() {
        if (!isEmpty()) {
            return detailsDump.pop();
        }
        return null;
    }

    public boolean isEmpty() {
        if (detailsDump.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean isVisitedBySc() {
        return visitedBySc1;
    }

    public void setVisitedBySc(boolean visitedBySc1) {
        this.visitedBySc1 = visitedBySc1;
    }

    public boolean isVisitedByGener() {
        return visitedByGener;
    }

    public void setVisitedByGener(boolean visitedByGener) {
        this.visitedByGener = visitedByGener;
    }
}