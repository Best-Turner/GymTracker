package com.example.gymtracker.exception.customException;

import java.time.Instant;

public record ErrorResponse(String code, String message, Instant timestamp) {
}
