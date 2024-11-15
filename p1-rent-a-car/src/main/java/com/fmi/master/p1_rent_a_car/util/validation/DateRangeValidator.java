package com.fmi.master.p1_rent_a_car.util.validation;

import com.fmi.master.p1_rent_a_car.dto.CreateOfferDTO;
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
