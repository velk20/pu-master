package org.fmi.rentacarextended.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.fmi.rentacarextended.validators.ValidDateRange;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Login user entity")
public class LoginUserDTO {
    @NotEmpty(message = "Username is required.")
    private String username;
    @NotEmpty(message = "Password is required.")
    private String password;
}
