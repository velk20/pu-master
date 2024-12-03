package org.fmi.rentacarextended.mappers;

import org.fmi.rentacarextended.models.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Offer.builder()
                .id(rs.getInt("id"))
                .carId(rs.getInt("car_id"))
                .userId(rs.getInt("user_id"))
                .price(rs.getDouble("price"))
                .additionalPrice(rs.getDouble("additional_price"))
                .totalPrice(rs.getDouble("total_price"))
                .startDate(rs.getObject("start_date", LocalDate.class))
                .endDate(rs.getObject("end_date", LocalDate.class))
                .isAccepted(rs.getBoolean("is_accepted"))
                .build();
    }
}
