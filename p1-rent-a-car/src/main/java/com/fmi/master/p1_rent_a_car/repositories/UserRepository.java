package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.mappers.UserRowMapper;
import com.fmi.master.p1_rent_a_car.utils.UserSqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate db;

    public UserRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<User> getUserById(int id) {
        String sql = String.format(UserSqlUtil.GET_USER_BY_ID, id);

        List<User> users = db.query(sql, new UserRowMapper());
        return users.stream().findFirst();
    }

    public List<User> getAllUsers() {
        String sql = String.format(UserSqlUtil.GET_ALL_USERS);

        return db.query(sql, new UserRowMapper());
    }

    public boolean createUser(User user) {
        String sql = String.format(UserSqlUtil.CREATE_USER,
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
        String sql = String.format(UserSqlUtil.UPDATE_USER,
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
       String sql = String.format(UserSqlUtil.DELETE_USER, 0, id);
       db.execute(sql);


       return true;
    }
}
