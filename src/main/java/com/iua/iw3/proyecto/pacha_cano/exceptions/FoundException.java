package com.iua.iw3.proyecto.pacha_cano.exceptions;

public class FoundException extends Exception {
    public FoundException() { }

    public FoundException(String message) {
        super(message);
    }

    public FoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoundException(Throwable cause) {
        super(cause);
    }

    public FoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
