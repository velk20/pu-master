package com.fmi.master.p1_rent_a_car.models;

public enum CityEnum {
    PLOVDIV("Plovdiv"),
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

    public static boolean exists(String cityName) {
        for (CityEnum city : CityEnum.values()) {
            if (city.getDisplayName().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }
}
