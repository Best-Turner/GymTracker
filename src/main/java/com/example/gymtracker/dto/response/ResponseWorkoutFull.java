package com.example.gymtracker.dto.response;

import java.time.LocalDate;
import java.util.List;

public record ResponseWorkoutFull(Long id, LocalDate date, String duration, String type,
                                  List<ExerciseWithSetsResponse> exercises) {
}
