package com.fmi.master.solarparks.exception;

public class CustomerNotFoundException extends RuntimeException {
    private String message;

    public CustomerNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
