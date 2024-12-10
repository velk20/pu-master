package org.fmi.streamline.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.streamline.repositories.UserRoleRepository;

public class ValidateUserRoleValidator implements ConstraintValidator<ValidateUserRole, String> {
    private final UserRoleRepository userRoleRepository;

    public ValidateUserRoleValidator(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRoleRepository.findUserRoleByUserRole(value).isPresent();
    }
}
