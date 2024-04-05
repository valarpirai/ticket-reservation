package com.bee.reservation.exception;

public class InvalidPayloadException extends Exception {
    public InvalidPayloadException(String errorMessage) {
        super(errorMessage);
    }
}
