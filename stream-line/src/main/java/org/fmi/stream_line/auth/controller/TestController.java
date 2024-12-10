package org.fmi.stream_line.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Testing Controller")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class TestController {

    @GetMapping("/welcome")
    @Operation(summary = "Test welcome ", security = @SecurityRequirement(name = "bearerAuth"))
    public String welcome() {
        return "Welcome page";
    }

    @GetMapping("/user/profile")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "User profile is shown here.";
    }
}
