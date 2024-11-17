package com.fmi.master.p1_rent_a_car.util;

public class OfferSqlUtil {
    public static final String GET_OFFER_BY_ID = "SELECT * FROM tb_offers WHERE is_active = 1 AND id = %s";
    public static final String GET_ALL_OFFERS_BY_USER_ID = "SELECT * FROM tb_offers WHERE is_active = 1 AND user_id = %s";
    public static final String CREATE_OFFER = "INSERT INTO tb_offers (car_id, user_id, price, additional_price, total_price, start_date, end_date, is_accepted) VALUES (%s, %s, %s, %s, %s, '%s', '%s', %s)";
    public static final String ACCEPT_OFFER = "UPDATE tb_offers SET is_accepted = TRUE WHERE is_active = 1 AND id = %s";
    public static final String DELETE_OFFER = "UPDATE tb_offers SET is_active = %s WHERE id = %s";

}
