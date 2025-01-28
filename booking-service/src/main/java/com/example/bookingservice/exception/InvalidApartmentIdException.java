package com.example.bookingservice.exception;

public class InvalidApartmentIdException extends RuntimeException {
    public InvalidApartmentIdException(String message) {
        super(message);
    }
}
