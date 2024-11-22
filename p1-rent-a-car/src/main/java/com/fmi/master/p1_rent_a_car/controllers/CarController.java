package com.fmi.master.p1_rent_a_car.controllers;

import com.fmi.master.p1_rent_a_car.models.Car;
import com.fmi.master.p1_rent_a_car.services.CarService;
import com.fmi.master.p1_rent_a_car.utils.AppResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation( summary = "Get all available cars by userId",
                description =  "Retrieves all available cars that are in the same city as the user's city")
    public ResponseEntity<?> getAllCarsByCity(@PathVariable int userId) {
        List<Car> carsByCity = carService.getAllCarsByCity(userId);

        return AppResponseUtil.success()
                .withData(carsByCity)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by ID")
    public ResponseEntity<?> getCarById(@PathVariable int id){
        Car car = carService.getCarById(id);

        return AppResponseUtil.success()
                .withData(car)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a new car")
    public ResponseEntity<?> createCar(@Valid @RequestBody Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }
        if (!carService.createCar(car)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Car was not successfully created")
                    .build();
        }

        return AppResponseUtil.created()
                .withMessage("Car successfully created")
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing car")
    public ResponseEntity<?> updateCar(@PathVariable int id, @Valid @RequestBody Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }

        if (!carService.updateCar(id, car)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Car update was not successful")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("Car successfully updated")
                .withData(this.carService.getCarById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete existing car by ID")
    public ResponseEntity<?> deleteCar(@PathVariable int id) {
        if (!carService.deleteCar(id)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Car was not successfully deleted")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("Car successfully deleted")
                .build();
    }
}
