package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.exception.WorkoutNotFoundException;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.repository.WorkoutRepository;
import com.example.gymtracker.service.ClientService;
import com.example.gymtracker.service.CoachService;
import com.example.gymtracker.service.WorkoutService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutMapper mapper;
    private final WorkoutRepository repository;
    private final CoachService coachService;
    private final ClientService clientService;
    private final EntityManager entityManager;

    @Override
    public ResponseWorkoutDto save(RequestWorkoutDto requestWorkoutDto) {
        Long clientId = requestWorkoutDto.clientId();
        Client clientById = clientService.getClientById(clientId);// если не существует -> бросится ClientNotFoundException
        Workout workoutEntity = mapper.toEntity(requestWorkoutDto);
        Integer maybeCoachId = requestWorkoutDto.coachId();
        if (maybeCoachId == null || coachService.isExist(maybeCoachId)) {
            workoutEntity.setCoach(null);
        } else {
            workoutEntity.setCoach(coachService.getCoachById(maybeCoachId));
        }
        workoutEntity.setClient(clientById);
        return mapper.toDto(repository.save(workoutEntity));
    }

    @Override
    public ResponseWorkoutDto getById(Long id) {
        return mapper.toDto(getWorkoutOrThrow(id));
    }

    @Override
    public List<ResponseWorkoutDto> getAll() {
        List<Workout> workouts = repository.findAll();
        if (workouts.isEmpty()) {
            return Collections.emptyList();
        }
        List<ResponseWorkoutDto> workoutDtoList = new ArrayList<>();
        for (Workout workout : workouts) {
            workoutDtoList.add(mapper.toDto(workout));
        }
        return workoutDtoList;
    }

    @Override
    public void deleteById(Long entityId) {
        repository.delete(getWorkoutOrThrow(entityId));
    }

    @Override
    public ResponseWorkoutDto update(Long id, RequestWorkoutDto updatedDto) {
        Workout existingWorkout = getWorkoutOrThrow(id);
        mapper.updateWorkout(updatedDto, existingWorkout);
        return mapper.toDto(repository.save(existingWorkout));
    }


    private Workout getWorkoutOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout not found with id:" + id));
    }
}
