package org.art.java_core.design.patterns.chain_of_responsibility;

/**
 * Chain of Responsibility pattern (from GoF) - simple code example.
 * Simple Logging System simulation.
 */
public class SimpleLogSystem {

    public static void main(String[] args) {

        SMSLogger smsLogger = new SMSLogger(LogLevel.ERROR);
        FileLogger fileLogger = new FileLogger(LogLevel.DEBUG);
        EmailLogger emailLogger = new EmailLogger(LogLevel.INFO);

        smsLogger.setNext(fileLogger);
        fileLogger.setNext(emailLogger);

        smsLogger.writeMessage("Data processing. No issues were detected...", LogLevel.INFO);
        smsLogger.writeMessage("Data processing. Debug mode...", LogLevel.DEBUG);
        smsLogger.writeMessage("Data processing failed! Some critical errors appeared...", LogLevel.ERROR);
    }
}

class LogLevel {

    static final int ERROR = 1;
    static final int DEBUG = 2;
    static final int INFO = 3;
}

interface LoggerIF {

    void writeMessage(String message, int level);
}

class SMSLogger implements LoggerIF {

    private int priority;
    private LoggerIF next;

    SMSLogger(int priority) {
        this.priority = priority;
    }

    public void setNext(LoggerIF next) {
        this.next = next;
    }

    public void writeMessage(String message, int level) {
        if (level <= priority) {
            System.out.println("SMS Logger: " + message);
        }
        if (next != null) {
            next.writeMessage(message, level);
        }
    }
}

class FileLogger implements LoggerIF {

    private int priority;
    private LoggerIF next;

    FileLogger(int priority) {
        this.priority = priority;
    }

    public void setNext(LoggerIF next) {
        this.next = next;
    }

    public void writeMessage(String message, int level) {
        if (level <= priority) {
            System.out.println("File Logger: " + message);
        }
        if (next != null) {
            next.writeMessage(message, level);
        }
    }
}

class EmailLogger implements LoggerIF {

    private int priority;
    private LoggerIF next;

    EmailLogger(int priority) {
        this.priority = priority;
    }

    public void setNext(LoggerIF next) {
        this.next = next;
    }

    public void writeMessage(String message, int level) {
        if (level <= priority) {
            System.out.println("Email Logger: " + message);
        }
        if (next != null) {
            next.writeMessage(message, level);
        }
    }
}