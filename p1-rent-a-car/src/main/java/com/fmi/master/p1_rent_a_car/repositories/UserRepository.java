package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final String GET_USER_BY_ID = "SELECT * FROM tb_users WHERE is_active = 1 AND id = ?";
    private final String GET_ALL_USERS = "SELECT * FROM tb_users WHERE is_active = 1";
    private final String CREATE_USER = """
                                       INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents) 
                                       VALUES (?, ?, ?, ?, ?, ?)
                                       """;
    private final String UPDATE_USER = """
                                       UPDATE tb_users 
                                       SET first_name = ?, last_name = ?, city = ?, phone = ?, years = ?, previous_accidents = ? 
                                       WHERE id = ?
                                       """;
    private final String DELETE_USER = "UPDATE tb_users SET is_active = ? WHERE id = ?";

    private final JdbcTemplate db;

    public UserRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<User> getUserById(int id) {
        List<User> users = db.query(GET_USER_BY_ID, ps -> ps.setInt(1, id), new UserRowMapper());
        return users.stream().findFirst();
    }

    public List<User> getAllUsers() {
        return db.query(GET_ALL_USERS, new UserRowMapper());
    }

    public boolean createUser(User user) {
        int rows = db.update(CREATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents());
        return rows > 0;
    }

    public boolean updateUser(int id, User user) {
        int rows = db.update(UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents(),
                id);
        return rows > 0;
    }

    public boolean deleteUser(int id) {
        int rows = db.update(DELETE_USER, 0, id);
        return rows > 0;
    }
}
