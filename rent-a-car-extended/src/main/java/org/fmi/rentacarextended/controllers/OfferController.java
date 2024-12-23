package org.fmi.rentacarextended.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.rentacarextended.dtos.CreateOfferDTO;
import org.fmi.rentacarextended.models.Offer;
import org.fmi.rentacarextended.services.OfferService;
import org.fmi.rentacarextended.utils.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Offer Controller", description = "Operations related to offer management")
@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get all offers by userId",
                description = "Retrieves all offers that have been proposed to a certain user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all offers by userId",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with this ID doesn't exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getAllOffersByUserId(@Parameter(description = "ID of the user")@PathVariable int userId) {
        List<Offer> allOffersByUserId = offerService.getAllOffersByUserId(userId);

        return AppResponseUtil.success()
                .withData(allOffersByUserId)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requested offer by ID",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Offer with requested ID not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getOfferById(@Parameter(description = "ID of the offer")@PathVariable int id) {
        Offer offer = offerService.getOfferById(id);

        return AppResponseUtil.success()
                .withData(offer)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new offer",
                description = "Creates a new offer by userId, carId, startDate and endDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer successfully created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Offer is not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Car or User with requested ID was not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
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
    @Operation(summary = "Accept already proposed offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer successfully accepted",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Offer with requested ID not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> acceptOffer(@Parameter(description = "ID of the offer")@PathVariable int offerId) {
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
    @Operation(summary = "Delete existing offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer successfully deleted",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Offer with request ID not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> deleteOffer(@Parameter(description = "ID of the offer")@PathVariable int offerId) {
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
