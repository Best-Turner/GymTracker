package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;

public interface WorkoutService extends EntityService<Long, RequestWorkoutDto, ResponseWorkoutDto> {
}
