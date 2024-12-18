package org.fmi.streamline.dtos.user;

import jakarta.validation.constraints.*;
import lombok.*;
import org.fmi.streamline.util.validation.UniquePhoneNumber;
import org.fmi.streamline.util.validation.UniqueUserEmail;
import org.fmi.streamline.util.validation.UniqueUsername;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
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
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    public ProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ProfileDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ProfileDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public ProfileDTO setAge(Integer age) {
        this.age = age;
        return this;
    }
}
