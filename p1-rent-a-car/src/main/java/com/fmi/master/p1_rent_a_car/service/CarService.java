package com.fmi.master.p1_rent_a_car.service;

import com.fmi.master.p1_rent_a_car.entity.Car;
import com.fmi.master.p1_rent_a_car.entity.User;
import com.fmi.master.p1_rent_a_car.mappers.CarRowMapper;
import com.fmi.master.p1_rent_a_car.model.CityEnum;
import com.fmi.master.p1_rent_a_car.util.CarSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final Logger logger = LoggerFactory.getLogger(CarService.class);
    private final JdbcTemplate db;
    private final UserService userService;
    public CarService(JdbcTemplate db, UserService userService) {
        this.db = db;
        this.userService = userService;
    }

    // Method to get a car by its ID
    public Car getCarById(int id) {
        String sql = String.format(CarSqlUtil.GET_CAR_BY_ID, id);

        List<Car> cars = db.query(sql, new CarRowMapper());
        if (cars.isEmpty()){
            return null;
        }

        return cars.get(0);
    }

    // Method to get all cars by city
    public List<Car> getAllCarsByCity(int userId) {
        User user = userService.getUserById(userId);
        String city = user.getCity();
        if (CityEnum.exists(city)){
            String sql = String.format(CarSqlUtil.GET_CAR_BY_CITY, city);
            return db.query(sql, new CarRowMapper());
        }

        return null;

    }

    // Method to create a new car
    public boolean createCar(Car car) {
        String sql = String.format(CarSqlUtil.CREATE_CAR,
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getCity());
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    // Method to update an existing car
    public boolean updateCar(int id, Car car) {
        String sql = String.format(CarSqlUtil.UPDATE_CAR, car.getBrand(), car.getModel(), car.getYear(), car.getPricePerDay(), car.getCity(), id);
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    // Method to delete a car
    public boolean deleteCar(int carId) {
        String sql = String.format(CarSqlUtil.DELETE_CAR, 0, carId);
        try {
            db.execute(sql);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
