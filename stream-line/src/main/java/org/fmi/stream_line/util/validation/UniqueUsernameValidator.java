package org.fmi.stream_line.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.stream_line.entities.UserEntity;
import org.fmi.stream_line.repositories.UserRepository;

import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {
    private final UserRepository userRepository;

    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(value);

        return userEntityOptional.isEmpty();
    }
}
