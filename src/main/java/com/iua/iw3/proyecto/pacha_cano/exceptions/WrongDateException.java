package com.iua.iw3.proyecto.pacha_cano.exceptions;

public class WrongDateException extends Exception {
    public WrongDateException() { }

    public WrongDateException(String message) {
        super(message);
    }

    public WrongDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongDateException(Throwable cause) {
        super(cause);
    }

    public WrongDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
