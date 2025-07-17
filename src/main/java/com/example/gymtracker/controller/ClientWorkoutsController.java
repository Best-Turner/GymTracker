package com.example.gymtracker.controller;

import com.example.gymtracker.service.ClientWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clients/{clientId}/workouts", produces = "application/json")
@AllArgsConstructor
public class ClientWorkoutsController {

    private final ClientWorkoutService clientWorkoutService;


    @GetMapping
    public ResponseEntity<List<?>> getWorkoutsByClientId(@PathVariable Long clientId,
                                                         @RequestParam(required = false, defaultValue = "default")
                                                         String detail) {
        return switch (detail) {
            case "full" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsFullByClientId(clientId));
            case "exercise" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsWithExerciseByClientId(clientId));
            default -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsByClientId(clientId));
        };
    }
}
