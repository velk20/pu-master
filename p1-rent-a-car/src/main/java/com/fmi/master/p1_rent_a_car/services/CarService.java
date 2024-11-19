package com.fmi.master.p1_rent_a_car.services;

import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.models.CityEnum;
import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.exceptions.CarNotFoundException;
import com.fmi.master.p1_rent_a_car.exceptions.UserNotFoundException;
import com.fmi.master.p1_rent_a_car.repositories.CarRepository;
import com.fmi.master.p1_rent_a_car.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;


    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Car getCarById(int id) {
        return this.carRepository
                .getCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id:" + id + " not found"));
    }

    public List<Car> getAllCarsByCity(int userId) {
        User user = userRepository
                    .getUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        String city = user.getCity();
        if (!CityEnum.exists(city)) {
            return null;
        }

        return carRepository.getAllCarsByCity(city);
    }

    public boolean createCar(Car car) {
        return this.carRepository.createCar(car);
    }

    public boolean updateCar(int id, Car car) {
        getCarById(id);

        return this.carRepository.updateCar(id, car);
    }

    public boolean deleteCar(int carId) {
        getCarById(carId);

        return this.carRepository.deleteCar(carId);
    }
}
