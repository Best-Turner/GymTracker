package com.example.gymtracker.dto.request;

import java.time.Duration;
import java.time.LocalDate;

public record RequestWorkoutDto(LocalDate date, Duration duration, String type, Long clientId, Integer coachId) {
}
