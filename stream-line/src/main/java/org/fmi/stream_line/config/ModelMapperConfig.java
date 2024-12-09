package org.fmi.stream_line.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Optionally configure ModelMapper for strict matching or other optimizations
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // Enable matching fields directly
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // Allow private fields
                .setSkipNullEnabled(true); // Ignore null values during mapping

        return modelMapper;
    }
}
