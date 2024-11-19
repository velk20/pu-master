package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.mappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final String GET_USER_BY_ID = "SELECT * FROM tb_users WHERE is_active = 1 AND id = %s";
    private final String GET_ALL_USERS = "SELECT * FROM tb_users WHERE is_active = 1";
    private final String CREATE_USER = "INSERT INTO tb_users (first_name, last_name, city, phone, years, previous_accidents) VALUES ('%s', '%s', '%s', '%s', %d, %b)";
    private final String UPDATE_USER = "UPDATE tb_users SET first_name = '%s', last_name = '%s', city = '%s', phone = '%s', years = %d, previous_accidents = %b WHERE id = %s";
    private final String DELETE_USER = "UPDATE tb_users SET is_active = %s WHERE id = %s";

    private final JdbcTemplate db;

    public UserRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<User> getUserById(int id) {
        String sql = String.format(this.GET_USER_BY_ID, id);

        List<User> users = db.query(sql, new UserRowMapper());
        return users.stream().findFirst();
    }

    public List<User> getAllUsers() {
        String sql = String.format(this.GET_ALL_USERS);

        return db.query(sql, new UserRowMapper());
    }

    public boolean createUser(User user) {
        String sql = String.format(this.CREATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents());

        db.execute(sql);
        return true;
    }

    public boolean updateUser(int id, User user) {
        String sql = String.format(this.UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents(),
                id);

        db.execute(sql);
        return true;
    }

    public boolean deleteUser(int id) {
       String sql = String.format(this.DELETE_USER, 0, id);
       db.execute(sql);


       return true;
    }
}
