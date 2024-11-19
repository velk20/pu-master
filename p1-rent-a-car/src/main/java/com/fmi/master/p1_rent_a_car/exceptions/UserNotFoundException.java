package com.fmi.master.p1_rent_a_car.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private String message;
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
