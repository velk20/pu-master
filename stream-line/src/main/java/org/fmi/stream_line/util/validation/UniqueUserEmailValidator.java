package org.fmi.stream_line.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.stream_line.entities.UserEntity;
import org.fmi.stream_line.repositories.UserRepository;

import java.util.Optional;


public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail,String> {
    private final UserRepository userRepository;


    public UniqueUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //! FIX THE EMAIL CHECKER IF THE USER IS CURRENT LOGGED USER
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(value);

        return userEntityOptional.isEmpty();
    }
}
