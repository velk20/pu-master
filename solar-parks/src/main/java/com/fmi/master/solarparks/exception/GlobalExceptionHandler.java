package com.fmi.master.solarparks.exception;

import com.fmi.master.solarparks.http.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<?> handleContactNotFoundException(ContactNotFoundException ex) {
        return AppResponse.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return AppResponse.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return AppResponse.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(SiteNotFoundException.class)
    public ResponseEntity<?> handleSiteNotFoundException(SiteNotFoundException ex) {
        return AppResponse.error(HttpStatus.NOT_FOUND)
                .withMessage(ex.getMessage())
                .build();
    }

    // General handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {

        return AppResponse.error(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDetailedMessage(ex.getMessage())
                .withMessage("An unexpected error occurred")
                .build();
    }

}
