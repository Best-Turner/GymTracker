package com.example.gymtracker.exception;

import java.time.Instant;

public record ErrorResponse(String code, String message, Instant timestamp) {
}
