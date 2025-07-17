package com.example.gymtracker.controller;

import com.example.gymtracker.dto.response.ResponseWorkoutDto;
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
    public ResponseEntity<List<ResponseWorkoutDto>> getWorkoutsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok()
                .body(clientWorkoutService.workoutsByClientId(clientId));
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<?> getWorkoutByIdAndClientId(@PathVariable("clientId") Long clientId,
                                                       @PathVariable("workoutId") Long workoutId,
                                                       @RequestParam(required = false, defaultValue = "default") String detail) {

        return switch (detail) {
            case "full" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutFullByClientIdAndWorkoutId(clientId, workoutId));
            case "exercise" -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutWithExerciseByClientIdAndWorkoutId(clientId, workoutId));
            default -> ResponseEntity.ok()
                    .body(clientWorkoutService.workoutsByClientId(clientId));
        };
    }
}
