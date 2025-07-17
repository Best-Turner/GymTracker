package com.example.gymtracker.dto.response;

import java.time.Duration;
import java.time.LocalDate;

public record ResponseWorkoutDto(Long id, LocalDate date, String duration, String type, Integer coachId) {
}
