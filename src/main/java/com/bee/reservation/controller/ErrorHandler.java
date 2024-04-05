package com.bee.reservation.controller;

import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = { NotFoundException.class,
            SeatNotAvailableException.class,
            UserAlreadyExistsException.class })
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NotFoundException || ex instanceof SeatNotAvailableException) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        } else if (ex instanceof UserAlreadyExistsException) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
