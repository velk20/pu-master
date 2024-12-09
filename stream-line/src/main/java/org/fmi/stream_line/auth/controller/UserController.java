package org.fmi.stream_line.auth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")

public class UserController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome page";
    }

    @GetMapping("/user/profile")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "User profile is shown here.";
    }
}
