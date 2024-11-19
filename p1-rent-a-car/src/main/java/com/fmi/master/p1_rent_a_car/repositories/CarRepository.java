package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.mappers.CarRowMapper;
import com.fmi.master.p1_rent_a_car.utils.CarSqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private final JdbcTemplate db;


    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Car> getCarById(int id) {
        String sql = String.format(CarSqlUtil.GET_CAR_BY_ID, id);

        List<Car> cars = db.query(sql, new CarRowMapper());
        return cars.stream().findFirst();
    }

    public List<Car> getAllCarsByCity(String city) {
        String sql = String.format(CarSqlUtil.GET_CAR_BY_CITY, city);
        return db.query(sql, new CarRowMapper());
    }

    public boolean createCar(Car car) {
        String sql = String.format(CarSqlUtil.CREATE_CAR,
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getCity());
        db.execute(sql);

        return true;
    }

    public boolean updateCar(int id, Car car) {
        String sql = String.format(CarSqlUtil.UPDATE_CAR, car.getBrand(), car.getModel(), car.getYear(), car.getPricePerDay(), car.getCity(), id);
        db.execute(sql);

        return true;
    }

    public boolean deleteCar(int carId) {
        String sql = String.format(CarSqlUtil.DELETE_CAR, 0, carId);
        db.execute(sql);

        return true;
    }
}
