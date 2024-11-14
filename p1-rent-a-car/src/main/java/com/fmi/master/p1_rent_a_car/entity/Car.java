package com.fmi.master.p1_rent_a_car.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Car {
    private int id;
    private String brand;
    private String model;
    private String city;
    private int year;
    private double pricePerDay;
}
