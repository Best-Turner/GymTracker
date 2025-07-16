package com.example.gymtracker.exception.customException;

import com.example.gymtracker.exception.customException.EntityNotFoundException;

public class CoachNotFoundException extends EntityNotFoundException {
    public CoachNotFoundException(String message) {
        super(message);
    }
}
