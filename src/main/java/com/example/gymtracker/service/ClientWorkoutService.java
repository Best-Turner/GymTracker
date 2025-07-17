package com.example.gymtracker.service;

import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;
import com.example.gymtracker.exception.customException.AccessDeniedException;
import com.example.gymtracker.exception.customException.ClientNotFoundException;
import com.example.gymtracker.exception.customException.WorkoutNotFoundException;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClientWorkoutService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper mapper;

    public ResponseWorkoutFull workoutFullByClientIdAndWorkoutId(Long clientId, Long workoutId) {
        checkClientByIdOrElseThrow(clientId);
        getValidatedWorkout(clientId, workoutId);
        return mapper.toWorkoutFull(workoutRepository.getWorkoutByClientIdAndId(clientId, workoutId));
    }


    public ResponseWorkoutWithExercise workoutWithExerciseByClientIdAndWorkoutId(Long clientId, Long workoutId) {
        checkClientByIdOrElseThrow(clientId);
        getValidatedWorkout(clientId, workoutId);
        return mapper.toWorkoutWithExercise(workoutRepository.findWithExercisesByClientId(clientId, workoutId));
    }

    public List<ResponseWorkoutDto> workoutsByClientId(Long clientId) {
        checkClientByIdOrElseThrow(clientId);
        return workoutRepository.findByClientId(clientId).stream()
                .map(mapper::toDto).toList();
    }


    private void getValidatedWorkout(Long clientId, Long workoutId) {
        checkWorkoutByIdOrElseThrow(workoutId);
        workoutRepository.findById(workoutId)
                .filter(workout -> Objects.equals(workout.getClient().getId(), clientId))
                .orElseThrow(() -> new AccessDeniedException("Workout does not belong to client"));
    }

    private void checkClientByIdOrElseThrow(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException("Client not found with id  = " + clientId);
        }
    }

    private void checkWorkoutByIdOrElseThrow(Long workoutId) {
        if (!workoutRepository.existsById(workoutId)) {
            throw new WorkoutNotFoundException("Workout not found with ID " + workoutId);
        }
    }
}
