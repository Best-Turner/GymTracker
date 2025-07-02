package com.example.gymtracker.model;

import java.util.Arrays;
import java.util.Optional;

public enum Specialization {

    GENERAL_PHYSICAL_TRAINING("Общая физическая подготовка (ОФП)"),

    SPECIAL_PHYSICAL_TRAINING("Специальная физическая подготовка (СФП)"),

    STRENGTH_TRAINING("Силовая подготовка"),

    ENDURANCE("Выносливость"),

    SPEED_STRENGTH_QUALITIES("Скоростно-силовые качества");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public static Optional<Specialization> fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(sp -> sp.displayName.equals(displayName))
                .findFirst();
    }

    public String getSpecializationName() {
        return displayName;
    }
}
