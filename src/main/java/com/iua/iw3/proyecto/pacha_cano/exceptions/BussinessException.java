package com.iua.iw3.proyecto.pacha_cano.exceptions;

public class BussinessException extends Exception {
    public BussinessException() { }

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinessException(Throwable cause) {
        super(cause);
    }

    public BussinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
