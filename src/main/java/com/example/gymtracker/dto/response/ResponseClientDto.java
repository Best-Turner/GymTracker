package com.example.gymtracker.dto.response;

import com.example.gymtracker.model.Gender;

import java.time.LocalDate;
import java.time.Period;

public record ResponseClientDto(Long id,
                                String name,
                                Gender gender,
                                LocalDate birthDate,
                                int age) {

    public ResponseClientDto(Long id, String name, Gender gender, LocalDate birthDate) {
        this(
                id,
                name,
                gender,
                birthDate,
                Period.between(birthDate, LocalDate.now()).getYears()
        );
    }
}