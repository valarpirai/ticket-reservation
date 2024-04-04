package com.bee.reservation.exception;

public class SeatNotAvailableException extends Exception {

    public SeatNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
