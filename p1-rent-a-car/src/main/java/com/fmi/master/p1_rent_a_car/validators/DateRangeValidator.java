package com.fmi.master.p1_rent_a_car.validators;

import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof CreateOfferDTO offer) {
            LocalDate startDate = offer.getStartDate();
            LocalDate endDate = offer.getEndDate();

            if (startDate != null && endDate != null) {
                return !endDate.isBefore(startDate);
            }
        }
        return true;
    }
}
