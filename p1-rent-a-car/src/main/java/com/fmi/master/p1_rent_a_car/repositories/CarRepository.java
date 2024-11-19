package com.fmi.master.p1_rent_a_car.repositories;

import com.fmi.master.p1_rent_a_car.mappers.CarRowMapper;
import com.fmi.master.p1_rent_a_car.models.Car;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {

    private final String GET_CAR_BY_ID = "SELECT * FROM tb_cars WHERE is_active = 1 AND id = ?";
    private final String GET_CARS_BY_CITY = "SELECT * FROM tb_cars WHERE is_active = 1 AND city = ?";
    private final String CREATE_CAR = "INSERT INTO tb_cars (brand, model, create_year, price_per_day, city) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_CAR = "UPDATE tb_cars SET brand = ?, model = ?, create_year = ?, price_per_day = ?, city = ? WHERE id = ?";
    private final String DELETE_CAR = "UPDATE tb_cars SET is_active = ? WHERE id = ?";

    private final JdbcTemplate db;

    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Optional<Car> getCarById(int id) {
        List<Car> cars = db.query(GET_CAR_BY_ID, ps -> ps.setInt(1, id), new CarRowMapper());
        return cars.stream().findFirst();
    }

    public List<Car> getAllCarsByCity(String city) {
        return db.query(GET_CARS_BY_CITY, ps -> ps.setString(1, city), new CarRowMapper());
    }

    public boolean createCar(Car car) {
        int rows = db.update(CREATE_CAR,
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getCity());
        return rows > 0;
    }

    public boolean updateCar(int id, Car car) {
        int rows = db.update(UPDATE_CAR,
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getPricePerDay(),
                car.getCity(),
                id);
        return rows > 0;
    }

    public boolean deleteCar(int carId) {
        int rows = db.update(DELETE_CAR, 0, carId);
        return rows > 0;
    }
}
