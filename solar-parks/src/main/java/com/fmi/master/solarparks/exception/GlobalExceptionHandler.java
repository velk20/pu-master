package com.fmi.master.solarparks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<String> handleContactNotFoundException(ContactNotFoundException ex) {
        return new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SiteNotFoundException.class)
    public ResponseEntity<String> handleSiteNotFoundException(SiteNotFoundException ex) {
        return new ResponseEntity<>("Site not found", HttpStatus.NOT_FOUND);
    }

    // General handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
