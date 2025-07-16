package com.example.gymtracker.service;

import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientWorkoutService {

    private final ClientRepository clientRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper mapper;


    public List<ResponseWorkoutFull> workoutsFullByClientId(Long id) {
    }


    public List<ResponseWorkoutWithExercise> workoutsWithExerciseByClientId(Long id) {
    }

    public List<ResponseWorkoutDto> workoutsByClientId(Long id) {
    }


}
