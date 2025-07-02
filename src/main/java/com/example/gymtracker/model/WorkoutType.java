package com.example.gymtracker.model;

import java.util.Arrays;
import java.util.Optional;

public enum WorkoutType {
    GENERAL_PHYSICAL_TRAINING("Общая физическая подготовка (ОФП)"),
    SPECIAL_PHYSICAL_TRAINING("Специальная физическая подготовка (СФП)"),
    STRENGTH_TRAINING("Силовая подготовка"),
    ENDURANCE_TRAINING("Выносливость"),
    SPEED_STRENGTH_QUALITIES("Скоростно-силовые качества"),
    TECHNICAL_TRAINING("Техническая подготовка"),
    TACTICAL_TRAINING("Тактическая подготовка"),
    RECOVERY_TRAINING("Восстановительная тренировка"),
    MENTAL_TRAINING("Психологическая подготовка");

    private final String description;

    WorkoutType(String description) {
        this.description = description;
    }

    public static Optional<WorkoutType> fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(type -> type.description.equals(displayName))
                .findFirst();
    }

    public String getDescription() {
        return description;
    }

}
