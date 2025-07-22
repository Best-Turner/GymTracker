package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetDetailsResponse;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;
import com.example.gymtracker.mapper.ExerciseSetMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/clients/{clientId}/workouts/{workoutId}/exercises-set",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClientWorkoutExerciseSetController {


    private final ClientWorkoutExerciseSetsService clientWorkoutExerciseSetsService;
    private final ExerciseSetMapper mapper;


    @GetMapping
    public ResponseEntity<List<ExerciseSetShortResponse>> getExerciseSets(@PathVariable("workoutId") Long workoutId,
                                                                          @PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(clientWorkoutExerciseSetsService.clientExerciseSets(clientId, workoutId));
    }

    @GetMapping("/{exerciseSetId}")
    public ResponseEntity<ExerciseSetDetailsResponse> getExerciseSetById(@PathVariable("workoutId") Long workoutId,
                                                                         @PathVariable("clientId") Long clientId,
                                                                         @PathVariable("exerciseSetId") Long exerciseSetId) {
        return ResponseEntity.ok(clientWorkoutExerciseSetsService.exerciseSetById(clientId, workoutId, exerciseSetId));
    }

    @DeleteMapping("/{exerciseSetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientExerciseSetById(@PathVariable("workoutId") Long workoutId,
                                            @PathVariable("clientId") Long clientId,
                                            @PathVariable("exerciseSetId") Long exerciseSetId) {
        clientWorkoutExerciseSetsService.delete(clientId, workoutId, exerciseSetId);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ExerciseSetShortResponse> createExerciseSet(@PathVariable("clientId") Long clientId,
                                                                      @PathVariable("workoutId") Long workoutId,
                                                                      @RequestBody RequestExerciseSetDto exerciseSetDto) {
        ExerciseSetShortResponse exerciseSetShortResponse =
                clientWorkoutExerciseSetsService.create(clientId, workoutId, exerciseSetDto);

        URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{exerciseSetId}")
                .buildAndExpand(exerciseSetShortResponse.id())
                .toUri();
        return ResponseEntity.created(uriLocation).body(exerciseSetShortResponse);
    }


    @PutMapping(value = "/{exerciseSetId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateExerciseSet(@PathVariable("clientId") Long clientId,
                                  @PathVariable("workoutId") Long workoutId,
                                  @PathVariable("exerciseSetId") Long exerciseSetId,
                                  @RequestBody RequestExerciseSetDto requestExerciseSetDto) {
        clientWorkoutExerciseSetsService.update(clientId, workoutId, exerciseSetId, requestExerciseSetDto);
    }

    @PatchMapping(value = "/{exerciseSetId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void patchExerciseSet(@PathVariable("clientId") Long clientId,
                                 @PathVariable("workoutId") Long workoutId,
                                 @PathVariable("exerciseSetId") Long exerciseSetId,
                                 @RequestBody Map<String, Object> detail) {
        clientWorkoutExerciseSetsService.patch(clientId, workoutId, exerciseSetId, detail);
    }
}

