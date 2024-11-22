package com.fmi.master.p1_rent_a_car.controllers;

import com.fmi.master.p1_rent_a_car.models.User;
import com.fmi.master.p1_rent_a_car.services.UserService;
import com.fmi.master.p1_rent_a_car.utils.AppResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<?> getById(@PathVariable int id) {
        User user = userService.getUserById(id);

        return AppResponseUtil.success()
                .withData(user)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<?> getAll() {
        List<User> allUsers = userService.getAllUsers();

        return AppResponseUtil.success()
                .withData(allUsers)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new user")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }

        if (!userService.createUser(user)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Creating user was not successful")
                    .build();
        }

        return AppResponseUtil.created()
                .withMessage("User created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing user")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }

        if (!userService.updateUser(id, user)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Updating user was not successful")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("User updated successfully")
                .withData(this.userService.getUserById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete existing user by ID")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (!userService.deleteUser(id)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Deleting user was not successful")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("User deleted successfully")
                .build();
    }
}
