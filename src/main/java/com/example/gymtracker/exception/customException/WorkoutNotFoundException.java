package com.example.gymtracker.exception;

import com.example.gymtracker.exception.customException.EntityNotFoundException;

public class WorkoutNotFoundException extends EntityNotFoundException {
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}
