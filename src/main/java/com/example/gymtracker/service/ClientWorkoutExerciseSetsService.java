package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetDetailsResponse;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;

import java.util.List;
import java.util.Map;

public interface ClientWorkoutExerciseSetsService {
    List<ExerciseSetShortResponse> clientExerciseSets(Long clientId, Long workoutId);
    ExerciseSetDetailsResponse exerciseSetById(Long clientId, Long workoutId, Long exerciseSetId);
    void delete(Long clientId, Long workoutId, Long exerciseSetId);

    ExerciseSetShortResponse createExerciseSet(Long clientId, Long workoutId, RequestExerciseSetDto exerciseSetDto);

    void updateExerciseSet(Long clientId, Long workoutId, Long exerciseSetId, RequestExerciseSetDto requestExerciseSetDto);
    void patchExerciseSet(Long clientId, Long workoutId, Long exerciseSetId, Map<String, Object> updates);
}

