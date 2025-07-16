package com.example.gymtracker.exception.customException;

public class WorkoutNotFoundException extends EntityNotFoundException {
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}
