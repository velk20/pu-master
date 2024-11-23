package com.fmi.master.p1_rent_a_car.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rent a Car REST API")
                        .version("1.0")
                        .description("Project #1 (Rent a car) of a pu-fmi-master class"));

    }
}
