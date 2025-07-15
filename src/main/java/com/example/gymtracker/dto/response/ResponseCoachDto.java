package com.example.gymtracker.dto.response;

import java.time.LocalDate;

public record ResponseCoachDto(int id, String name, String specialization, LocalDate hireDate, boolean active) {
}
