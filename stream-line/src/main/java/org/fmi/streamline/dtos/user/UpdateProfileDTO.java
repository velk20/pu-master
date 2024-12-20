package org.fmi.streamline.dtos.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileDTO {
    @NotEmpty(message = "First Name is required.")
    @Size(min = 3, max = 20, message = "First Name must be between 2 nad 20 symbols.")
    private String firstName;
    @NotEmpty(message = "Last Name is required.")
    @Size(min = 3, max = 20, message = "Last Name must be between 2 nad 20 symbols.")
    private String lastName;
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    private String email;
    @NotEmpty(message = "Username can't be empty.")
    @Size(min = 2, max = 20, message = "Must be between 2 and 20 symbols.")
    private String username;
    @NotEmpty(message = "Phone is required.")
    private String phone;
    @Min(value = 10, message = "Minimum age is 10.")
    @Max(value = 99, message = "Maximum age is 99.")
    @NotNull(message = "Age is required.")
    private Integer age;
}
