package com.fmi.master.solarparks.exception;

public class ContactNotFoundException extends RuntimeException {
    private String message;
    public ContactNotFoundException() {
    }

    public ContactNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
