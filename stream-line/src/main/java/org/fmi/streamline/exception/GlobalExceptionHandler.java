package org.fmi.streamline.exception;

import org.fmi.streamline.util.AppResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

//TODO fix ControllerAdvice
//@RestControllerAdvice(basePackages = {"org.fmi.streamline.controllers", "org.fmi.streamline.auth.controller"})
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }

    // General handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) throws Exception {
        // Skip handling SpringDoc exceptions entirely
        if (ex.getClass().getName().startsWith("org.springdoc")) {
            throw ex; // Re-throw to allow SpringDoc to handle it
        }

        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

        return AppResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDetailedMessage(ex.getMessage())
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage("An unexpected error occurred")
                .build();
    }
}
