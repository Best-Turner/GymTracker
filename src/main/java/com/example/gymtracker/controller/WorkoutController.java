package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/workouts", produces = "application/json")
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<ResponseWorkoutDto>> getAllWorkout() {
        List<ResponseWorkoutDto> workoutDtoList = workoutService.getAll();
        return workoutDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(workoutDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWorkoutDto> getWorkoutById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workoutService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable("id") Long id) {
        workoutService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWorkoutDto> saveWorkout(@RequestBody RequestWorkoutDto workoutDto, UriComponentsBuilder componentsBuilder) {
        ResponseWorkoutDto savedWorkout = workoutService.save(workoutDto);
        URI location = componentsBuilder.path("/api/workouts/{id}")
                .buildAndExpand(savedWorkout.id()).toUri();
        return ResponseEntity.created(location).body(savedWorkout);
    }


    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseWorkoutDto> updateWorkout(@PathVariable Long id, @RequestBody RequestWorkoutDto workoutDto) {
        return ResponseEntity.ok(workoutService.update(id, workoutDto));
    }
}
