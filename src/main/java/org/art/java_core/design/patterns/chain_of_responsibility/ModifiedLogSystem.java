package org.art.java_core.design.patterns.chain_of_responsibility;

/**
 * Chain of Responsibility pattern (from GoF) - simple code example.
 * Modified Logging System simulation.
 */
public class ModifiedLogSystem {

    public static void main(String[] args) {

        LoggerBase smsLogger = new ASMSLogger(LogLevel.ERROR);
        LoggerBase fileLogger = new AFileLogger(LogLevel.DEBUG);
        LoggerBase emailLogger = new AEmailLogger(LogLevel.INFO);

        smsLogger.setNext(fileLogger);
        fileLogger.setNext(emailLogger);

        smsLogger.writeMessage("Data processing. No issues were detected...", LogLevel.INFO);
        smsLogger.writeMessage("Data processing. Debug mode...", LogLevel.DEBUG);
        smsLogger.writeMessage("Data processing failed! Some critical errors appeared...", LogLevel.ERROR);
    }
}

abstract class LoggerBase {

    private int priority;
    private LoggerBase next;

    LoggerBase(int priority) {
        this.priority = priority;
    }

    void writeMessage(String message, int level) {
        if (level <= priority) {
            write(message);
        }
        if (next != null) {
            next.writeMessage(message, level);
        }
    }

    abstract void write(String message);

    void setNext(LoggerBase next) {
        this.next = next;
    }
}

class ASMSLogger extends LoggerBase {

    ASMSLogger(int priority) {
        super(priority);
    }

    void write(String message) {
        System.out.println("SMS Logger: " + message);
    }
}

class AFileLogger extends LoggerBase {

    AFileLogger(int priority) {
        super(priority);
    }

    void write(String message) {
        System.out.println("File Logger: " + message);
    }
}

class AEmailLogger extends LoggerBase {

    AEmailLogger(int priority) {
        super(priority);
    }

    void write(String message) {
        System.out.println("Email Logger: " + message);
    }
}
