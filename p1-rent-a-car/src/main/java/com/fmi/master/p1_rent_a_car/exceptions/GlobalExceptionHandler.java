package com.fmi.master.p1_rent_a_car.exceptions;

import com.fmi.master.p1_rent_a_car.utils.AppResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<?> handleCarNotFoundException(CarNotFoundException ex){
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<?> handleOfferNotFoundException(OfferNotFoundException ex){
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    // General handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        return AppResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDetailedMessage(ex.getMessage())
                .withMessage("An unexpected error occurred")
                .build();
    }
}
