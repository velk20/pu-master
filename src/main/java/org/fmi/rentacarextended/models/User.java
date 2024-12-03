package org.fmi.rentacarextended.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Schema(description = "username of the user", example = "a4katababy")
    @NotEmpty(message = "username is required.")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters")
    private String username;
    @Schema(description = "password of the user", example = "******")
    @NotEmpty(message = "password is required.")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters")
    private String password;
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
