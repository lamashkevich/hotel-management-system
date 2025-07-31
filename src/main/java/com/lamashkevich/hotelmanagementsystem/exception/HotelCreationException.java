package com.lamashkevich.hotelmanagementsystem.exception;

public class HotelCreationException extends RuntimeException {
    public HotelCreationException() {
        super("Failed to create hotel");
    }
}
