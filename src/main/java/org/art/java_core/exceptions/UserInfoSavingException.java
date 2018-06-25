package org.art.java_core.exceptions;

/**
 * Custom checked exception which is thrown in case of some
 * problems while user info saving.
 */
public class UserInfoSavingException extends Exception {

    public UserInfoSavingException() {}

    public UserInfoSavingException(String message) {
        super(message);
    }

    public UserInfoSavingException(Throwable cause) {
        super(cause);
    }

    public UserInfoSavingException(String message, Throwable cause) {
        super(message, cause);
    }
}
