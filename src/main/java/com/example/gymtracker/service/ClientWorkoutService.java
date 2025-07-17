package com.example.gymtracker.service;

import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;
import com.example.gymtracker.exception.customException.ClientNotFoundException;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ClientWorkoutService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper mapper;

    public List<ResponseWorkoutFull> workoutsFullByClientId(Long clientId) {
        checkClientByIdOrElseThrow(clientId); // если нет то бросится исключение ClientNotFoundException
        return workoutRepository.findByClientId(clientId).stream()
                .map(mapper::toWorkoutFull).toList();
    }


    public List<ResponseWorkoutWithExercise> workoutsWithExerciseByClientId(Long clientId) {
        checkClientByIdOrElseThrow(clientId);
        return workoutRepository.findWithExercisesByClientId(clientId).stream()
                .map(mapper::toWorkoutWithExercise).toList();
    }

    public List<ResponseWorkoutDto> workoutsByClientId(Long clientId) {
        checkClientByIdOrElseThrow(clientId);
        return workoutRepository.findByClientId(clientId).stream()
                .map(mapper::toDto).toList();
    }

    private void checkClientByIdOrElseThrow(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException("Client not found with id  = " + clientId);
        }
    }

}
