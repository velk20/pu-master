package com.fmi.master.p1_rent_a_car.exceptions;

public class OfferNotFoundException extends RuntimeException {
    private final String message;

    public OfferNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
