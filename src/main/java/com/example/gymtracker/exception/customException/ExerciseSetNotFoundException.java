package com.example.gymtracker.exception.customException;

public class ExerciseSetNotFoundException extends EntityNotFoundException {

    public ExerciseSetNotFoundException(String message) {
        super(message);
    }
}
