package com.fmi.master.p1_rent_a_car.utils;

public class CarSqlUtil {
    public static final String GET_CAR_BY_ID = "SELECT * FROM tb_cars WHERE is_active = 1 AND id = %s";
    public static final String GET_CAR_BY_CITY = "SELECT * FROM tb_cars WHERE is_active = 1 AND city = '%s'";
    public static final String CREATE_CAR = "INSERT INTO tb_cars (brand, model, create_year, price_per_day, city) VALUES ('%s', '%s', %s, %s, '%s')";
    public static final String UPDATE_CAR = "UPDATE tb_cars SET brand = '%s', model = '%s', create_year = %s, price_per_day = %s, city = '%s' WHERE id = %s";
    public static final String DELETE_CAR = "UPDATE tb_cars SET is_active = %s WHERE id = %s";

}
