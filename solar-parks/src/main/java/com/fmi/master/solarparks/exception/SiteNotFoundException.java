package com.fmi.master.solarparks.exception;

public class SiteNotFoundException extends RuntimeException{
    private String message;

    public SiteNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
