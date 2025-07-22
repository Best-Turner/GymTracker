package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;
import com.example.gymtracker.model.ExerciseSet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExerciseSetMapper extends EntityMapper<RequestExerciseSetDto, ExerciseSet, ExerciseSetShortResponse> {

//TODO написать реализацию
    @Override
    ExerciseSetShortResponse toDto(ExerciseSet exerciseSet);

    @Override
    ExerciseSet toEntity(RequestExerciseSetDto exerciseSetDto);
}
