package com.fmi.master.p1_rent_a_car.exceptions;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException {
    private final String message;
    public OfferNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
