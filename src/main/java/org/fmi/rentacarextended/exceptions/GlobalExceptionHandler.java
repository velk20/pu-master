package org.fmi.rentacarextended.exceptions;

import org.fmi.rentacarextended.utils.AppResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex){
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidCityException.class)
    public ResponseEntity<?> handleInvalidCityException(InvalidCityException ex){
        return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }

    // General handler for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        return AppResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDetailedMessage(ex.getMessage())
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage("An unexpected error occurred")
                .build();
    }
}
