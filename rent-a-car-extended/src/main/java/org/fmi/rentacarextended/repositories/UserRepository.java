package org.fmi.rentacarextended.repositories;

import org.fmi.rentacarextended.mappers.UserRowMapper;
import org.fmi.rentacarextended.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final String GET_USER_BY_ID = "SELECT * FROM tb_users WHERE is_active = 1 AND id = ?";
    private final String GET_USER_BY_USERNAME = "SELECT * FROM tb_users WHERE is_active = 1 AND username = ?";
    private final String GET_ALL_USERS = "SELECT * FROM tb_users WHERE is_active = 1";
    private final String GET_LATEST_USER = "SELECT * FROM tb_users WHERE is_active = 1 ORDER BY id DESC LIMIT 1";
    private final String CREATE_USER = """
                                       INSERT INTO tb_users (username, 
                                                             password, 
                                                             first_name, 
                                                             last_name, 
                                                             city, 
                                                             phone, 
                                                             years, 
                                                             previous_accidents,
                                                             role) 
                                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                                       """;
    private final String UPDATE_USER = """
                                       UPDATE tb_users 
                                       SET username = ?,
                                           password = ?,
                                           first_name = ?, 
                                           last_name = ?, 
                                           city = ?, 
                                           phone = ?, 
                                           years = ?, 
                                           previous_accidents = ?,
                                           role = ?
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

    public Optional<User> getUserByUsername(String username) {
        List<User> users = db.query(GET_USER_BY_USERNAME, ps -> ps.setString(1, username), new UserRowMapper());
        return users.stream().findFirst();
    }

    public List<User> getAllUsers() {
        return db.query(GET_ALL_USERS, new UserRowMapper());
    }

    public boolean createUser(User user) {
        int rows = db.update(CREATE_USER,
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents(),
                user.getRole());
        return rows > 0;
    }

    public boolean updateUser(int id, User user) {
        int rows = db.update(UPDATE_USER,
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents(),
                user.getRole(),
                id);
        return rows > 0;
    }

    public boolean deleteUser(int id) {
        int rows = db.update(DELETE_USER, 0, id);
        return rows > 0;
    }

    public Optional<User> getLatest() {
        return db.query(GET_LATEST_USER, new UserRowMapper()).stream().findFirst();
    }
}
