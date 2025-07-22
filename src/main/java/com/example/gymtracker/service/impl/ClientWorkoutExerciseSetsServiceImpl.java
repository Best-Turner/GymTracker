package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetDetailsResponse;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;
import com.example.gymtracker.exception.customException.AccessDeniedException;
import com.example.gymtracker.exception.customException.ExerciseSetNotFoundException;
import com.example.gymtracker.exception.customException.WorkoutNotFoundException;
import com.example.gymtracker.mapper.ExerciseSetMapper;
import com.example.gymtracker.model.ExerciseSet;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.repository.ExerciseSetRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import com.example.gymtracker.service.ClientWorkoutExerciseSetsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ClientWorkoutExerciseSetsServiceImpl implements ClientWorkoutExerciseSetsService {

    private final ExerciseSetRepository exerciseSetRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseSetMapper mapper;

    @Override
    public List<ExerciseSetShortResponse> clientExerciseSets(Long clientId, Long workoutId) {
        return exerciseSetRepository.findByWorkoutIdAndWorkoutClientId(workoutId, clientId).stream()
                .map(mapper::toDto).toList();
    }
    @Override
    public ExerciseSetDetailsResponse exerciseSetById(Long clientId, Long workoutId, Long exerciseSetId) {
        return mapper.toDetailsEntity(checkingAccessRights(clientId, workoutId, exerciseSetId));
    }

    @Override
    public void delete(Long clientId, Long workoutId, Long exerciseSetId) {
        exerciseSetRepository.delete(checkingAccessRights(clientId, workoutId, exerciseSetId));
    }
    @Override
    public ExerciseSetShortResponse createExerciseSet(Long clientId, Long workoutId, RequestExerciseSetDto exerciseSetDto) {
        Workout workout = workoutRepository.getWorkoutByClientIdAndId(clientId, workoutId);
        if (workout == null) {
            throw new WorkoutNotFoundException("Workout not found with ID = " + workoutId);
        }

        if (!workout.getClient().getId().equals(clientId)) {
            throw new AccessDeniedException("The client with Id %d training does not belong".formatted(clientId));
        }

        ExerciseSet newExerciseSet = mapper.toEntity(exerciseSetDto);
        newExerciseSet.setWorkout(workout);

        ExerciseSet savedSet = exerciseSetRepository.save(newExerciseSet);
        return mapper.toDto(savedSet);
    }

    @Override
    public void updateExerciseSet(Long clientId, Long workoutId, Long exerciseSetId, RequestExerciseSetDto requestExerciseSetDto) {
        ExerciseSet exerciseSet = checkingAccessRights(clientId, workoutId, exerciseSetId);
        mapper.updateExerciseSet(requestExerciseSetDto, exerciseSet);
        exerciseSetRepository.save(exerciseSet);
    }

    @Override
    public void patchExerciseSet(Long clientId, Long workoutId, Long exerciseSetId, Map<String, Object> updates) {
        ExerciseSet exerciseSet = checkingAccessRights(clientId, workoutId, exerciseSetId);
        updates.forEach((key, value) -> {
            switch (key) {
                case "weight":
                    exerciseSet.setWeight((Double) value);
                    break;
                case "reps":
                    exerciseSet.setReps((Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        exerciseSetRepository.save(exerciseSet);
    }

    private ExerciseSet checkingAccessRights(Long clientId, Long workoutId, Long exerciseSetId) {
        ExerciseSet exerciseSetFromDb = exerciseSetRepository.findById(exerciseSetId)
                .orElseThrow(() ->
                        new ExerciseSetNotFoundException("ExerciseSet not found with ID = " + exerciseSetId));
        if (!exerciseSetFromDb.getWorkout().getId().equals(workoutId)) {
            throw new AccessDeniedException("This exercise set does not belong workout with ID = " + workoutId);
        }
        Workout workout = exerciseSetFromDb.getWorkout();
        if (!workout.getClient().getId().equals(clientId)) {
            throw new AccessDeniedException("This workout does not belong client with ID = " + clientId);
        }
        return exerciseSetFromDb;
    }
}
