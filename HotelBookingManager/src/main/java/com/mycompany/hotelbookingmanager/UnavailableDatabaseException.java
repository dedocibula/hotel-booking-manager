package com.mycompany.hotelbookingmanager;

/**
 *
 * @author Inky Ashizuki
 */
public class UnavailableDatabaseException extends Exception{

    /**
     *
     */
    public UnavailableDatabaseException() {
    }

    /**
     *
     * @param msg
     */
    public UnavailableDatabaseException(String msg) {
        super(msg);
    }

    /**
     *
     * @param cause
     */
    public UnavailableDatabaseException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public UnavailableDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
