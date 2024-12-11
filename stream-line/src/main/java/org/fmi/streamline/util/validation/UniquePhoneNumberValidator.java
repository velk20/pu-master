package org.fmi.streamline.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber,String> {
    private final UserRepository userRepository;

    public UniquePhoneNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserEntity> byPhone = userRepository.findByPhoneAndIsActiveTrue(value);
        return byPhone.isEmpty();
    }

}
