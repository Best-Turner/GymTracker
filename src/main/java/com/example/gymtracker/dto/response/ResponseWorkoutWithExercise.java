package com.example.gymtracker.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ResponseWorkoutWithExercise(Long id, LocalDate date, String duration, String type,
                                          List<ExerciseShortResponse> exercises) {
}
