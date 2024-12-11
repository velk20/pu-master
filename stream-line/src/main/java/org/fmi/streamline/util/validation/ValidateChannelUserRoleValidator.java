package org.fmi.streamline.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.streamline.entities.ChannelMembershipEntity;
import org.fmi.streamline.repositories.UserRoleRepository;

import java.util.Arrays;

public class ValidateChannelUserRoleValidator implements ConstraintValidator<ValidateChannelUserRole, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(ChannelMembershipEntity.Role.values()).anyMatch(role -> role.name().equalsIgnoreCase(value));
    }

}
