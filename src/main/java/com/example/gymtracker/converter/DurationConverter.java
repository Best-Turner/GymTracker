package com.example.gymtracker.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration attribute) {
        if (attribute == null) {
            return null;
        }
        long seconds = attribute.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d hours %d minutes %d seconds",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return (seconds < 0 ? "-" : "") + positive;
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            PGInterval interval = new PGInterval(dbData);
            long seconds = (long) (interval.getHours() * 3600 + interval.getMinutes() * 60 + interval.getSeconds());
            return Duration.ofSeconds(seconds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
