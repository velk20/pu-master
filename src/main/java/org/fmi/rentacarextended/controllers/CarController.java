package org.fmi.rentacarextended.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.rentacarextended.models.Car;
import org.fmi.rentacarextended.services.CarService;
import org.fmi.rentacarextended.utils.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Car Controller", description = "Operations related to car management")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved available cars",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Cars are not available in the user's city",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with requested userId not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getAllCarsByCity(@Parameter(description = "ID of the user")@PathVariable int userId) {
        List<Car> carsByCity = carService.getAllCarsByCity(userId);

        return AppResponseUtil.success()
                .withData(carsByCity)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved car by existing ID",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Car not found with requested ID",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
    })
    public ResponseEntity<?> getCarById(@Parameter(description = "ID of the car")@PathVariable int id){
        Car car = carService.getCarById(id);

        return AppResponseUtil.success()
                .withData(car)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car created successfully",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Car was not successfully created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully updated",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Car is not valid",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Cars with requested ID not found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
    })
    public ResponseEntity<?> updateCar(@Parameter(description = "ID of the car")@PathVariable int id, @Valid @RequestBody Car car, BindingResult bindingResult) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully deleted",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Car with requested ID not found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
    })
    public ResponseEntity<?> deleteCar(@Parameter(description = "ID of the car")@PathVariable int id) {
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
