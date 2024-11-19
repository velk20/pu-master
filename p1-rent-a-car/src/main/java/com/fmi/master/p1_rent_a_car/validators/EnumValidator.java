package com.fmi.master.p1_rent_a_car.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Assume null values are handled by @NotNull if required
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .anyMatch(name -> name.equalsIgnoreCase(value));
    }
}
