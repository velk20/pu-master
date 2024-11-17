package com.fmi.master.p1_rent_a_car.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private int id;
    @NotEmpty(message = "firstName is required.")
    private String firstName;
    @NotEmpty(message = "lastName is required.")
    private String lastName;
    @NotEmpty(message = "city is required.")
    private String city;
    @NotEmpty(message = "phone is required.")
    private String phone;
    @Positive(message = "years must be positive number.")
    private int years;
    @NotNull(message = "previousAccidents must be true or false")
    private Boolean previousAccidents;
}
