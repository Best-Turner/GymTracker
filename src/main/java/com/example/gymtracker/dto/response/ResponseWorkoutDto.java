package com.example.gymtracker.dto.response;

import com.example.gymtracker.model.WorkoutType;

import java.time.Duration;
import java.time.LocalDate;

public record ResponseWorkoutDto(Long id, LocalDate date, Duration duration, String type, Integer coachId) {
}
