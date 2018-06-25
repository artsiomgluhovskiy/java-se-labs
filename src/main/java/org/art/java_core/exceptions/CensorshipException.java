package org.art.java_core.exceptions;

/**
 * Custom checked exception which is thrown in case of a curse word
 * is found in the text.
 */
public class CensorshipException extends Exception {

    public CensorshipException() {}

    public CensorshipException(String message) {
        super(message);
    }

    public CensorshipException(Throwable cause) {
        super(cause);
    }

    public CensorshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
