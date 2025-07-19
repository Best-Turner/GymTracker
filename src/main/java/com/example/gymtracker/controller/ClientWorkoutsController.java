package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.service.ClientWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getClientWorkoutById(@PathVariable("clientId") Long clientId,
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


    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseWorkoutDto> createWorkout(@PathVariable("clientId") Long clientId,
                                                            @RequestBody RequestWorkoutDto requestWorkoutDto) {
        ResponseWorkoutDto createdWorkout = clientWorkoutService.createWorkout(clientId, requestWorkoutDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(createdWorkout.id())
                .toUri();
        return ResponseEntity.created(location).body(createdWorkout);
    }

    @DeleteMapping("/{workoutId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable("clientId") Long clientId,
                              @PathVariable("workoutId") Long workoutId) {
        clientWorkoutService.deleteClientWorkout(clientId, workoutId);
    }

    @PutMapping(value = "/{workoutId}", consumes = "application/json")
    public ResponseEntity<ResponseWorkoutDto> updateClientWorkout(@PathVariable("workoutId") Long workoutId,
                                                                  @PathVariable("clientId") Long clientId,
                                                                  @RequestBody RequestWorkoutDto requestWorkoutDto) {
        return ResponseEntity.ok(clientWorkoutService.updateWorckout(clientId, workoutId, requestWorkoutDto));
    }

    @PatchMapping(value = "/{workoutId}", consumes = "application/json")
    public ResponseEntity<ResponseWorkoutDto> updateClientWorkout(@PathVariable("workoutId") Long workoutId,
                                                                  @PathVariable("clientId") Long clientId,
                                                                  Map<String, Object> updates) {
        return ResponseEntity.ok(clientWorkoutService.patchWorckout(clientId, workoutId, updates));
    }
}
