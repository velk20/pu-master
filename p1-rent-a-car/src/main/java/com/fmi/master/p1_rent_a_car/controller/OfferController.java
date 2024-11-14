package com.fmi.master.p1_rent_a_car.controller;

import com.fmi.master.p1_rent_a_car.entity.Offer;
import com.fmi.master.p1_rent_a_car.entity.User;
import com.fmi.master.p1_rent_a_car.service.OfferService;
import com.fmi.master.p1_rent_a_car.util.AppResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllOffersByUserId(@PathVariable int userId) {
        List<Offer> allOffersByUserId = offerService.getAllOffersByUserId(userId);
        return AppResponse.success()
                .withData(allOffersByUserId)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable int id) {
        Offer offer = offerService.getOfferById(id);
        return AppResponse.success()
                .withData(offer)
                .build();
    }

    @PostMapping("/{userId}/{carId}")
    public ResponseEntity<?> createOffer(@PathVariable int userId,
                                         @PathVariable int carId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (offerService.createOffer(userId, carId, startDate, endDate)) {
            return AppResponse.created()
                    .withMessage("Offer successfully created")
                    .build();
        }

        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Offer was not successfully created")
                .build();
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable int offerId) {
        if (offerService.acceptOffer(offerId)) {
            return AppResponse.success()
                    .withMessage("Offer successfully accepted")
                    .build();
        }
        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Offer was not successfully accepted")
                .build();
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable int offerId) {
        if (offerService.deleteOffer(offerId)) {
            return AppResponse.success()
                    .withMessage("Offer successfully deleted")
                    .build();
        }
        return AppResponse.error(HttpStatus.BAD_REQUEST)
                .withMessage("Offer was not deleted")
                .build();
    }
}
