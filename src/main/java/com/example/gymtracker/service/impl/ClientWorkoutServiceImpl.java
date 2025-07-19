package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;
import com.example.gymtracker.exception.customException.*;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.model.WorkoutType;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.repository.CoachRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import com.example.gymtracker.service.ClientWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClientWorkoutServiceImpl implements ClientWorkoutService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper mapper;
    private final CoachRepository coachRepository;

    @Override
    public ResponseWorkoutFull clientWorkoutFullById(Long clientId, Long workoutId) {
        checkClientByIdOrElseThrow(clientId);
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        return mapper.toWorkoutFull(workoutRepository.getWorkoutByClientIdAndId(clientId, workoutId));
    }

    @Override
    public ResponseWorkoutWithExercise clientWorkoutWithExerciseById(Long clientId, Long workoutId) {
        checkClientByIdOrElseThrow(clientId);
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        return mapper.toWorkoutWithExercise(workoutRepository.findWithExercisesByClientId(clientId, workoutId));
    }

    @Override
    public ResponseWorkoutDto createWorkout(Long clientId, RequestWorkoutDto requestWorkoutDto) {
        checkClientByIdOrElseThrow(clientId);
        Workout workoutToSave = mapper.toEntity(requestWorkoutDto);
        Client ownerWorkout = clientRepository.findById(clientId).get();
        if (workoutToSave.getCoach() != null) {
            Integer coachId = workoutToSave.getCoach().getId();
            Coach coachToAppointment = coachRepository.findById(coachId)
                    .orElseThrow(() -> new CoachNotFoundException("Coach not found with ID = " + coachId));
            workoutToSave.setCoach(coachToAppointment);
        }
        workoutToSave.setClient(ownerWorkout);
        return mapper.toDto(workoutRepository.save(workoutToSave));
    }

    @Override
    public void deleteClientWorkout(Long clientId, Long workoutId) {
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        workoutRepository.deleteById(workoutId);
    }

    @Override
    public ResponseWorkoutDto updateWorkout(Long clientId, Long workoutId, RequestWorkoutDto requestWorkoutDto) {
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        Workout workout = workoutRepository.findById(workoutId).get();
        mapper.updateWorkout(requestWorkoutDto, workout);
        return mapper.toDto(workoutRepository.save(workout));
    }

    @Override
    public ResponseWorkoutDto patchWorkout(Long clientId, Long workoutId, Map<String, Object> updates) {
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        Workout workoutToPatch = workoutRepository.findById(workoutId).get();
        updates.forEach((key, value) -> {
            switch (key) {
                case "date":
                    workoutToPatch.setDate(LocalDate.parse((String) value));
                    break;
                case "duration":
                    workoutToPatch.setDuration(Duration.ofSeconds(Long.parseLong(value.toString())));
                    break;
                case "type":
                    workoutToPatch.setType(WorkoutType.fromDisplayName((String) value)
                            .orElseThrow(() -> new ValueCastException("This field value is incorrect + " + value)));
                    break;
                case "coachId":
                    Coach coach = coachRepository.findById(((Number) value).intValue())
                            .orElseThrow(() -> new CoachNotFoundException("Coach not found with ID =" + value));
                    workoutToPatch.setCoach(coach);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });
        return mapper.toDto(workoutRepository.save(workoutToPatch));
    }

    @Override
    public ResponseWorkoutDto clientWorkoutById(Long clientId, Long workoutId) {
        validateClientAndWorkoutOrElseThrow(clientId, workoutId);
        return mapper.toDto(workoutRepository.findById(workoutId).get());
    }

    @Override
    public List<ResponseWorkoutDto> clientWorkouts(Long clientId) {
        checkClientByIdOrElseThrow(clientId);
        return workoutRepository.findByClientId(clientId).stream()
                .map(mapper::toDto).toList();
    }


    private void validateClientAndWorkoutOrElseThrow(Long clientId, Long workoutId) {
        checkClientByIdOrElseThrow(clientId);
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
