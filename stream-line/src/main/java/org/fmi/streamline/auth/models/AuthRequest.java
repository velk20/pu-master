package org.fmi.streamline.auth.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "Username is required.")
    private String username;
    @NotEmpty(message = "Password is required.")
    private String password;
}
