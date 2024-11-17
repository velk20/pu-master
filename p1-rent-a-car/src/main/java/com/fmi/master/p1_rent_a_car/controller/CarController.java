package com.fmi.master.p1_rent_a_car.controller;

import com.fmi.master.p1_rent_a_car.entity.Car;
import com.fmi.master.p1_rent_a_car.exceptions.CarNotFoundException;
import com.fmi.master.p1_rent_a_car.service.CarService;
import com.fmi.master.p1_rent_a_car.util.AppResponse;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllCarsByCity(@PathVariable int userId) {
        List<Car> carsByCity = carService.getAllCarsByCity(userId);
        if (carsByCity == null)
        {
            return AppResponse.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Cars are not available in the user's city")
                    .build();
        }
        return AppResponse.success()
                .withData(carsByCity)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable int id){
        Car car = carService.getCarById(id)
                .orElseThrow(()->new CarNotFoundException("Car with id:" + id + " not found"));

        return AppResponse.success()
                .withData(car)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponse.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }
        if (!carService.createCar(car)) {
            return AppResponse.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Car creation was not successful")
                    .build();
        }

        return AppResponse.created()
                .withMessage("Car successfully created")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @Valid @RequestBody Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponse.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }

        if (!carService.updateCar(id, car)) {
            return AppResponse.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Car update was not successful")
                    .build();
        }

        return AppResponse.success()
                .withMessage("Car successfully updated")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        if (carService.deleteCar(id)) {
            return AppResponse.success()
                    .withMessage("Car successfully deleted")
                    .build();
        }

        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Car was not successfully deleted")
                .build();
    }
}
