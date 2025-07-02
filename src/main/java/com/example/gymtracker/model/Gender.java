package com.example.gymtracker.model;

public enum Gender {

    MALE("Мужской"),
    FEMALE("Женский");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
