package org.fmi.rentacarextended.mappers;

import org.fmi.rentacarextended.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .city(rs.getString("city"))
                .phone(rs.getString("phone"))
                .years(rs.getInt("years"))
                .previousAccidents(rs.getBoolean("previous_accidents"))
                .build();
    }
}