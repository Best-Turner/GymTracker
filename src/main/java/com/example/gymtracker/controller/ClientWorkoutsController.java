package com.example.gymtracker.controller;

import com.example.gymtracker.service.ClientWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/clients/{clientId}/workouts", produces = "application/json")
@AllArgsConstructor
public class ClientWorkoutsController {

    private final ClientWorkoutService clientWorkoutService;


    @GetMapping("/{id}/workouts")
    public ResponseEntity<?> getWorkoutsByClientId(@PathVariable Long id,
                                                   @RequestParam(required = false) String detail) {
        return switch (detail) {
            case "full" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsFullByClientId(id));
            case "exercise" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsWithExerciseByClientId(id));
            default -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsByClientId(id));
        };
    }
}
