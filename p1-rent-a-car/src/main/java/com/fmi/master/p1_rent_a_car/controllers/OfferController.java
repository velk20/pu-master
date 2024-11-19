package com.fmi.master.p1_rent_a_car.controllers;

import com.fmi.master.p1_rent_a_car.dtos.CreateOfferDTO;
import com.fmi.master.p1_rent_a_car.models.Offer;
import com.fmi.master.p1_rent_a_car.exceptions.OfferNotFoundException;
import com.fmi.master.p1_rent_a_car.services.OfferService;
import com.fmi.master.p1_rent_a_car.utils.AppResponseUtil;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

        return AppResponseUtil.success()
                .withData(allOffersByUserId)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable int id) {
        Offer offer = offerService.getOfferById(id);

        return AppResponseUtil.success()
                .withData(offer)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@Valid @RequestBody CreateOfferDTO createOfferDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessage)
                    .build();
        }
        if (!offerService.createOffer(createOfferDTO)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Offer was not successfully created")
                    .build();
        }

        return AppResponseUtil.created()
                .withMessage("Offer successfully created")
                .build();
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable int offerId) {
        if (!offerService.acceptOffer(offerId)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Offer was not successfully accepted")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("Offer successfully accepted")
                .withData(offerService.getOfferById(offerId))
                .build();
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable int offerId) {
        if (!offerService.deleteOffer(offerId)) {
            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withMessage("Offer was not deleted")
                    .build();
        }

        return AppResponseUtil.success()
                .withMessage("Offer successfully deleted")
                .build();
    }
}
