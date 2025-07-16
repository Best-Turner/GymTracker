package com.example.gymtracker.dto.response;

import java.util.List;

public record ExerciseWithSetsResponse(Long id, String name, String muscleGroup, List<ExerciseSetShortResponse> sets) {
}
