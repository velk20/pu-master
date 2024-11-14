package com.fmi.master.p1_rent_a_car.model;

public enum CityEnum {
    PLOVDIV("Plvdiv"),
    SOFIA("Sofia"),
    VARNA("Varna"),
    BURGAS("Burgas");

    private final String displayName;

    CityEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
