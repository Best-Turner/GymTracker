package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;

import java.util.List;
import java.util.Map;

public interface ClientWorkoutService {
    List<ResponseWorkoutDto> clientWorkouts(Long clientId);

    ResponseWorkoutFull clientWorkoutFullById(Long clientId, Long workoutId);

    ResponseWorkoutWithExercise clientWorkoutWithExerciseById(Long clientId, Long workoutId);

    ResponseWorkoutDto createWorkout(Long clientId, RequestWorkoutDto requestWorkoutDto);

    void deleteClientWorkout(Long clientId, Long workoutId);

    ResponseWorkoutDto updateWorkout(Long clientId, Long workoutId, RequestWorkoutDto requestWorkoutDto);

    ResponseWorkoutDto patchWorkout(Long clientId, Long workoutId, Map<String, Object> updates);

    ResponseWorkoutDto clientWorkoutById(Long clientId, Long workoutId);
}
