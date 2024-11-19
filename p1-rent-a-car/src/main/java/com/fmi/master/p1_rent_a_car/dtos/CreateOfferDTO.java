package com.fmi.master.p1_rent_a_car.dtos;

import com.fmi.master.p1_rent_a_car.validators.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ValidDateRange
public class CreateOfferDTO {
    @Positive(message = "userId is not valid.")
    private int userId;
    @Positive(message = "carId is not valid.")
    private int carId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "startDate is required.")
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "endDate is required.")
    private LocalDate endDate;
}
