package org.fmi.rentacarextended.services;


import org.fmi.rentacarextended.exceptions.EntityNotFoundException;
import org.fmi.rentacarextended.exceptions.InvalidCityException;
import org.fmi.rentacarextended.models.Car;
import org.fmi.rentacarextended.models.CityEnum;
import org.fmi.rentacarextended.models.User;
import org.fmi.rentacarextended.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserService userService;

    public CarService(CarRepository carRepository, UserService userService) {
        this.carRepository = carRepository;
        this.userService = userService;
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public Car getCarById(int id) {
        return this.carRepository
                .getCarById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with id:" + id + " not found"));
    }

    public List<Car> getAllCarsByCity(int userId) {
        User user = userService.getUserById(userId);

        String city = user.getCity();
        if (!CityEnum.exists(city)) {
            throw new InvalidCityException("Cars are not available in the user's city");
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
