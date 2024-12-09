package org.fmi.stream_line.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.stream_line.entities.UserEntity;
import org.fmi.stream_line.repositories.UserRepository;

import java.util.Optional;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber,String> {
    private final UserRepository userRepository;

    public UniquePhoneNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserEntity> byPhone = userRepository.findByPhone(value);
        return byPhone.isEmpty();
    }

}
