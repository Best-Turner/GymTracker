package com.example.gymtracker.dto.response;

public record ExerciseSetDetailsResponse(Long id, Double weight, Integer reps, String exerciseName, String muscleGroup,
                                         Long workoutId) {
}
