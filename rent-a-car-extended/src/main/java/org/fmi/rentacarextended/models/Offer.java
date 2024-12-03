package org.fmi.rentacarextended.models;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Offer {
    private int id;
    private int carId;
    private int userId;
    private double price;
    private double additionalPrice;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;
}
