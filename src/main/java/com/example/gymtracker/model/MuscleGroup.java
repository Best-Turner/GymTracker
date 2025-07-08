package com.example.gymtracker.model;

import java.util.Arrays;
import java.util.Optional;

public enum MuscleGroup {

    CHEST("Грудные мышцы"),
    BACK("Спина"),
    SHOULDERS("Плечи"),
    BICEPS("Бицепсы"),
    TRICEPS("Трицепсы"),
    FOREARMS("Предплечья"),
    ABS("Пресс"),
    QUADRICEPS("Четырёхглавая мышца бедра"),
    HAMSTRINGS("Задняя поверхность бедра"),
    GLUTES("Ягодичные мышцы"),
    CALVES("Икры"),
    NECK("Шея");

    private final String description;

    MuscleGroup(String description) {
        this.description = description;
    }

    public static Optional<MuscleGroup> fromDisplayName(String displayName) {
        return Arrays.stream(values()).filter(mg -> mg.getDescription().equals(displayName)).findFirst();
    }

    public String getDescription() {
        return description;
    }
}
