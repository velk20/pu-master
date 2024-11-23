package com.fmi.master.p1_rent_a_car.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmi.master.p1_rent_a_car.validators.ValidDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Offer entity")
public class CreateOfferDTO {
    @Positive(message = "userId is not valid.")
    @Schema(description = "Unique user identifier that will be the offer proposed to the user", example = "1")
    private int userId;
    @Positive(message = "carId is not valid.")
    @Schema(description = "Unique car identifier that will be the car proposed to the user", example = "1")
    private int carId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "startDate is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date user will start renting the car", example = "2024-10-01")
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "endDate is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "The date user will end renting the car", example = "2024-10-09")
    private LocalDate endDate;
}
