package com.example.gymtracker.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter(autoApply = true)
public class DurationIntervalConverter implements AttributeConverter<Duration, Long> {


    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return attribute.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return Duration.ofSeconds(dbData);
    }
}