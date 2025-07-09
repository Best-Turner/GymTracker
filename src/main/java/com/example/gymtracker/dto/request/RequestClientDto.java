package com.example.gymtracker.dto.request;

import com.example.gymtracker.model.Gender;

import java.time.LocalDate;

public record RequestClientDto(String name, Gender gender, LocalDate birthDate) {
}
