package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.mappers.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private final String GET_CAR_BY_ID = "SELECT * FROM tb_cars WHERE is_active = 1 AND id = %s";
    private final String GET_CAR_BY_CITY = "SELECT * FROM tb_cars WHERE is_active = 1 AND city = '%s'";
    private final String CREATE_CAR = "INSERT INTO tb_cars (brand, model, create_year, price_per_day, city) VALUES ('%s', '%s', %s, %s, '%s')";
    private final String UPDATE_CAR = "UPDATE tb_cars SET brand = '%s', model = '%s', create_year = %s, price_per_day = %s, city = '%s' WHERE id = %s";
    private final String DELETE_CAR = "UPDATE tb_cars SET is_active = %s WHERE id = %s";

    private final JdbcTemplate db;


    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Car> getCarById(int id) {
        String sql = String.format(this.GET_CAR_BY_ID, id);

        List<Car> cars = db.query(sql, new CarRowMapper());
        return cars.stream().findFirst();
    }

    public List<Car> getAllCarsByCity(String city) {
        String sql = String.format(this.GET_CAR_BY_CITY, city);
        return db.query(sql, new CarRowMapper());
    }

    public boolean createCar(Car car) {
        String sql = String.format(this.CREATE_CAR,
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getCity());
        db.execute(sql);

        return true;
    }

    public boolean updateCar(int id, Car car) {
        String sql = String.format(this.UPDATE_CAR, car.getBrand(), car.getModel(), car.getYear(), car.getPricePerDay(), car.getCity(), id);
        db.execute(sql);

        return true;
    }

    public boolean deleteCar(int carId) {
        String sql = String.format(this.DELETE_CAR, 0, carId);
        db.execute(sql);

        return true;
    }
}
