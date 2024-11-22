package com.fmi.master.p1_rent_a_car.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "User entity")
public class User {
    @Schema(description = "User's ID", example = "1")
    private int id;
    @NotEmpty(message = "firstName is required.")
    @Schema(description = "The first name of the user", example = "Angel")
    private String firstName;
    @NotEmpty(message = "lastName is required.")
    @Schema(description = "The last name of the user", example = "Mladenov")
    private String lastName;
    @NotEmpty(message = "city is required.")
    @Schema(description = "The city of the user", example = "Plovdiv")
    private String city;
    @NotEmpty(message = "phone is required.")
    @Schema(description = "The phone number of the user", example = "0888888888")
    private String phone;
    @Positive(message = "years must be positive number.")
    @Schema(description = "The years old of the user", example = "23")
    private int years;
    @NotNull(message = "previousAccidents must be true or false")
    @Schema(description = "If the user has previous accidents", example = "true/false")
    private Boolean previousAccidents;

}
