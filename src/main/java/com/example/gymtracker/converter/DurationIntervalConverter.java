package com.example.gymtracker.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGInterval;

import java.time.Duration;

@Converter(autoApply = true)
public class DurationIntervalConverter implements AttributeConverter<Duration, Object> {

    @Override
    public Object convertToDatabaseColumn(Duration attribute) {
        if (attribute == null) {
            return null;
        }
        // Преобразуем Duration в строку формата PostgreSQL INTERVAL, например: '1 day 02:03:04'
        long seconds = attribute.getSeconds();
        int nanos = attribute.getNano();

        long absSeconds = Math.abs(seconds);
        long days = absSeconds / 86400;
        long hours = (absSeconds % 86400) / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;

        // Формируем строку интервала
        String interval = String.format("%s%d days %02d:%02d:%02d.%09d",
                seconds < 0 ? "-" : "",
                days, hours, minutes, secs, nanos);

        return interval;
    }

    @Override
    public Duration convertToEntityAttribute(Object dbData) {
        if (dbData == null) {
            return null;
        }
        if (dbData instanceof String) {
            // Парсим строку интервала PostgreSQL
            return parsePgIntervalString((String) dbData);
        }
        if (dbData instanceof java.sql.Timestamp) {
            // Иногда может прийти Timestamp, но обычно нет
            return Duration.ZERO;
        }
        throw new IllegalArgumentException("Unsupported type for Duration conversion: " + dbData.getClass());
    }

    private Duration parsePgIntervalString(String interval) {
        // Пример простого парсинга: "1 day 02:03:04.123456789"
        // Для сложных случаев лучше использовать библиотеку или собственный парсер.
        try {
            String[] parts = interval.split(" ");
            long days = 0;
            String timePart;

            if (parts.length == 2 && parts[1].contains(":")) {
                // Формат: "1 day 02:03:04.123456789"
                days = Long.parseLong(parts[0]);
                timePart = parts[1];
            } else {
                // Формат: "02:03:04.123456789"
                timePart = interval;
            }

            String[] hms = timePart.split(":");
            long hours = Long.parseLong(hms[0]);
            long minutes = Long.parseLong(hms[1]);

            String[] secAndNano = hms[2].split("\\.");
            long seconds = Long.parseLong(secAndNano[0]);
            int nanos = 0;
            if (secAndNano.length > 1) {
                String nanoStr = secAndNano[1];
                // Дополняем до наносекунд (9 знаков)
                nanoStr = (nanoStr + "000000000").substring(0, 9);
                nanos = Integer.parseInt(nanoStr);
            }

            long totalSeconds = days * 86400 + hours * 3600 + minutes * 60 + seconds;
            return Duration.ofSeconds(totalSeconds, nanos);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse PostgreSQL interval string: " + interval, e);
        }
    }
}