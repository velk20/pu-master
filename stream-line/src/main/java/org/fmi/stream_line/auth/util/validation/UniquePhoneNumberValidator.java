package org.fmi.stream_line.auth.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.stream_line.auth.entity.UserEntity;
import org.fmi.stream_line.auth.repository.UserRepository;

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
