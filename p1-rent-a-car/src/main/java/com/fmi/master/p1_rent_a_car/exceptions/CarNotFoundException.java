package com.fmi.master.p1_rent_a_car.exceptions;

public class CarNotFoundException extends RuntimeException {
    private final String message;
    public CarNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
