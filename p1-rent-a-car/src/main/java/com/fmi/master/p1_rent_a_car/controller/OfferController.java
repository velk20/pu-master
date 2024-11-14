package com.fmi.master.p1_rent_a_car.controller;

import com.fmi.master.p1_rent_a_car.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllOffersByUserId(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createOffer(@RequestBody User user, int carId,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return null;
    }

    @PostMapping("/{offerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable String offerId) {
        return null;
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable String offerId) {
        return null;
    }
}
