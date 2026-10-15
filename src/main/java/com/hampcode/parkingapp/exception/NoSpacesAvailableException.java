package com.hampcode.parkingapp.exception;

// 409
public class NoSpacesAvailableException extends RuntimeException {

    public NoSpacesAvailableException(String message) {
        super(message);
    }
}
