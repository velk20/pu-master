package org.fmi.stream_line;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Stream Line API",
				version = "1.0",
				description = "API documentation for my Stream Line project",
				contact = @Contact(name = "Angel Mladenov", email = "angelmladenov3@gmail.com")
		),
		security = @SecurityRequirement(name = "bearerAuth")

)
@SecurityScheme(
		name = "bearerAuth", // Reference name for the security requirement
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT" // Optional: indicate the format of the token
)
@SpringBootApplication
public class StreamLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamLineApplication.class, args);
	}

}
