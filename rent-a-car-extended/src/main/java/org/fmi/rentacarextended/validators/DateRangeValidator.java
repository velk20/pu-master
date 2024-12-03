package org.fmi.rentacarextended.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fmi.rentacarextended.dtos.CreateOfferDTO;

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
