package org.fmi.streamline.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.fmi.streamline.util.validation.FieldMatch;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldMatch(first = "newPassword", second = "confirmPassword", message = "Passwords do not match.")
public class ChangeUserPasswordDTO {
    @NotEmpty(message = "Old password is required.")
    private String oldPassword;
    @NotEmpty(message = "New password is required.")
    private String newPassword;
    @NotEmpty(message = "Confirm password is required.")
    private String confirmPassword;
}
