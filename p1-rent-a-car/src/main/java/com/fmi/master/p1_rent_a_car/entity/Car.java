package com.fmi.master.p1_rent_a_car.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Car {
    private int id;
    @NotEmpty(message = "brand is required.")
    private String brand;
    @NotEmpty(message = "model is required.")
    private String model;
    @NotEmpty(message = "city is required.")
    private String city;
    @Min(value = 1980,message = "Minimum year is 1980.")
    @Max(value = 2100,message = "Maximum year is 2100.")
    private int year;
    @Positive(message = "pricePerDay must be positive.")
    private double pricePerDay;
}
