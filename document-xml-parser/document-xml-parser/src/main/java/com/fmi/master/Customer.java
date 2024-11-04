package com.fmi.master;

@JSONEntity
public class Customer {
    @JsonField
    private String name = "Angel";
    private String role ="Manger";
    @JsonField
    private double salary = 33333;
}
