package com.example.gymtracker.exception.customException;

public class CoachNotFoundException extends EntityNotFoundException {
    public CoachNotFoundException(String message) {
        super(message);
    }
}
