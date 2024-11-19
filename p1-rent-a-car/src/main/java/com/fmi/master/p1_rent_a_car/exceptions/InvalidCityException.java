package com.fmi.master.p1_rent_a_car.exceptions;

public class InvalidCityException extends RuntimeException {
    public InvalidCityException(String message) {
        super(message);
    }
}
