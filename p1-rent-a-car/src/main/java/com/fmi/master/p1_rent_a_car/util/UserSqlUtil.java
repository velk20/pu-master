package com.fmi.master.p1_rent_a_car.util;

public class UserSqlUtil {
    public static final String GET_USER_BY_ID = "SELECT * FROM tb_users WHERE is_active = 1 AND id = %s";
    public static final String GET_ALL_USERS = "SELECT * FROM tb_users WHERE is_active = 1";
    public static final String CREATE_USER = "INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents) VALUES ('%s', '%s', '%s', '%s', %d, %b)";
    public static final String UPDATE_USER = "UPDATE tb_users SET first_name = '%s', last_name = '%s', city = '%s', phone = '%s', years = %d, previous_accidents = %b WHERE id = %s";
    public static final String DELETE_USER = "UPDATE tb_users SET is_active = %s WHERE id = %s";
}
