package com.fmi.master.p1_rent_a_car.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String phone;
    private int years;
    private boolean previousAccidents;
}
