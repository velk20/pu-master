package org.fmi.rentacarextended.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.rentacarextended.dtos.LoginUserDTO;
import org.fmi.rentacarextended.models.User;
import org.fmi.rentacarextended.services.UserService;
import org.fmi.rentacarextended.utils.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Controller", description = "Operations related to user management")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requested user by ID",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with requested ID not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID of the user")@PathVariable int id) {
        User user = userService.getUserById(id);

        return AppResponseUtil.success()
                .withData(user)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all users",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getAll() {
        List<User> allUsers = userService.getAllUsers();

        return AppResponseUtil.success()
                .withData(allUsers)
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", description = "Incorrect username or password",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with username and password didn't exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }

        return AppResponseUtil.success()
                .withData(userService.login(user))
                .withMessage("User logged successfully")
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "User is not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
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
                .withData(userService.getLatestUser())
                .withMessage("User created successfully")
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "User is not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with requested ID was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> update(@Parameter(description = "ID of the user")@PathVariable int id, @Valid @RequestBody User user, BindingResult bindingResult) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with requested ID was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the user")@PathVariable int id) {
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
