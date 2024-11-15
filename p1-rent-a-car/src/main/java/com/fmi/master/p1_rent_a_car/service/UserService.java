package com.fmi.master.p1_rent_a_car.service;

import com.fmi.master.p1_rent_a_car.entity.User;
import com.fmi.master.p1_rent_a_car.exceptions.UserNotFoundException;
import com.fmi.master.p1_rent_a_car.mappers.UserRowMapper;
import com.fmi.master.p1_rent_a_car.util.UserSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JdbcTemplate db;

    public UserService(JdbcTemplate db) {
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
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean updateUser(int id, User user) {
        getUserById(id).orElseThrow(()->new UserNotFoundException("User with id:" + id + " not found"));

        String sql = String.format(UserSqlUtil.UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getCity(),
                user.getPhone(),
                user.getYears(),
                user.getPreviousAccidents(),
                id);

        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean deleteUser(int id) {
        getUserById(id).orElseThrow(()->new UserNotFoundException("User with id:" + id + " not found"));

        String sql = String.format(UserSqlUtil.DELETE_USER, 0, id);
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}
