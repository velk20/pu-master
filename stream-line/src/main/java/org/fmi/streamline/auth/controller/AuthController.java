package org.fmi.streamline.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.streamline.auth.models.AuthRequest;
import org.fmi.streamline.auth.models.AuthResponse;
import org.fmi.streamline.auth.service.AuthService;
import org.fmi.streamline.auth.models.RegisterDTO;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.util.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Authentication Controller", description = "Operations related to authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register User")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        AuthResponse registered = authService.register(dto);
        return AppResponseUtil.created()
                .withData(registered)
                .withMessage("User successfully registered")
                .build();
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate/Login User")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }
        AuthResponse authenticated = authService.authenticate(authRequest);
        return AppResponseUtil.success()
                .withMessage("User successfully logged in")
                .withData(authenticated)
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }

}
