package org.fmi.rentacarextended.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fmi.rentacarextended.validators.EnumValue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Car entity")
public class Car {
    @Schema(description = "Car's ID", example = "1")
    private int id;
    @NotEmpty(message = "brand is required.")
    @Schema(description = "The brand of the car", example = "Toyota")
    private String brand;
    @NotEmpty(message = "model is required.")
    @Schema(description = "The model of the car", example = "Auris")
    private String model;
    @NotEmpty(message = "city is required.")
    @EnumValue(enumClass = CityEnum.class, message = "city must be between: Sofia, Plovdiv, Varna, Burgas")
    @Schema(description = "The city that the car is located", example = "Sofia/Plovdiv/Varna/Burgas")
    private String city;
    @Min(value = 1980, message = "Minimum year is 1980.")
    @Max(value = 2100, message = "Maximum year is 2100.")
    @Schema(description = "The year car was made", example = "2006")
    private int year;
    @Positive(message = "pricePerDay must be positive.")
    @Schema(description = "The price per day for renting the car", example = "70")
    private double pricePerDay;
    @NotEmpty(message = "Image URL is required.")
    @Schema(description = "The image URL of the car")
    private String imageUrl;
}
