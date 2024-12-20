package org.fmi.streamline.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.fmi.streamline.util.validation.UniquePhoneNumber;
import org.fmi.streamline.util.validation.UniqueUserEmail;
import org.fmi.streamline.util.validation.UniqueUsername;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDTO {
    private String id;
    @NotEmpty(message = "First Name is required.")
    @Size(min = 3, max = 20, message = "First Name must be between 2 nad 20 symbols.")
    private String firstName;
    @NotEmpty(message = "Last Name is required.")
    @Size(min = 3, max = 20, message = "Last Name must be between 2 nad 20 symbols.")
    private String lastName;
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    @UniqueUserEmail(message = "User email is already taken.")
    private String email;
    @NotEmpty(message = "Username can't be empty.")
    @Size(min = 2, max = 20, message = "Must be between 2 and 20 symbols.")
    @UniqueUsername(message = "Username is already taken.")
    private String username;
    @NotEmpty(message = "Phone is required.")
    @UniquePhoneNumber(message = "Phone number is already taken.")
    private String phone;
    @Min(value = 10, message = "Minimum age is 10.")
    @Max(value = 99, message = "Maximum age is 99.")
    @NotNull(message = "Age is required.")
    private Integer age;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastModified;
    private String role;
    private List<FriendDTO> friends;

    public UserDetailDTO setFriends(List<FriendDTO> friends) {
        this.friends = friends;
        return this;
    }
}
