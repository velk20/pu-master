package com.fmi.master.p1_rent_a_car.controller;

import com.fmi.master.p1_rent_a_car.entity.Car;
import com.fmi.master.p1_rent_a_car.service.CarService;
import com.fmi.master.p1_rent_a_car.util.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/user/{city}")
    public ResponseEntity<?> getAllCarsByCity(@PathVariable String city) {
        List<Car> carsByCity = carService.getAllCarsByCity(city);
        return AppResponse.success()
                .withData(carsByCity)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable int id){
        Car car = carService.getCarById(id);
        return AppResponse.success()
                .withData(car)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createCar(@RequestBody Car car) {
        if (carService.createCar(car)) {
            return AppResponse.created()
                    .withMessage("Car successfully created")
                    .build();
        }

        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Car creation was not successful")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody Car car) {
        if (carService.updateCar(id, car)) {
            return AppResponse.success()
                    .withMessage("Car successfully updated")
                    .build();
        }

        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Car update was not successful")
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return AppResponse.success()
                .withMessage("Car successfully deleted")
                .build();
    }
}