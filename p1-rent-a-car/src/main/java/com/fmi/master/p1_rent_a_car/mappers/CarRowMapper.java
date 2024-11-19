package com.fmi.master.p1_rent_a_car.mappers;

import com.fmi.master.p1_rent_a_car.models.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Car.builder()
                .id(rs.getInt("id"))
                .brand(rs.getString("brand"))
                .model(rs.getString("model"))
                .year(rs.getInt("create_year"))
                .city(rs.getString("city"))
                .pricePerDay(rs.getDouble("price_per_day"))
                .build();
    }
}
