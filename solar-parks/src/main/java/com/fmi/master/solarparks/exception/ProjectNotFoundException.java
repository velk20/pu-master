package com.fmi.master.solarparks.exception;

public class ProjectNotFoundException extends RuntimeException {
    private String message;

    public ProjectNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
