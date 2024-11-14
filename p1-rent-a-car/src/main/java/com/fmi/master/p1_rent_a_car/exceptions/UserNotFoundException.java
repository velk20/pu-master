package com.fmi.master.p1_rent_a_car.exceptions;

public class UserNotFoundException extends RuntimeException {
    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
