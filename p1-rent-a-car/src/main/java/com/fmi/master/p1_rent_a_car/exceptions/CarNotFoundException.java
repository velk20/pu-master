package com.fmi.master.p1_rent_a_car.exceptions;

import lombok.Getter;

@Getter
public class CarNotFoundException extends RuntimeException {
    private final String message;
    public CarNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
