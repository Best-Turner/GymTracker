package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestExercisePatchDto;
import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetDetailsResponse;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;
import com.example.gymtracker.model.Exercise;
import com.example.gymtracker.model.ExerciseSet;
import com.example.gymtracker.model.Workout;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExerciseSetMapper extends EntityMapper<RequestExerciseSetDto, ExerciseSet, ExerciseSetShortResponse> {

    @Override
    ExerciseSetShortResponse toDto(ExerciseSet exerciseSet);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workout", ignore = true)
    @Mapping(target = "exercise", expression = "java(idToExercise(exerciseSetDto.exerciseId()))")
    ExerciseSet toEntity(RequestExerciseSetDto exerciseSetDto);

    @Mapping(source = "exercise", target = "exerciseName", qualifiedByName = "exerciseToExerciseName")
    @Mapping(target = "muscleGroup", expression = "java(exerciseToString(exerciseSet.getExercise()))")
    @Mapping(target = "workoutId", expression = "java(workoutToId(exerciseSet.getWorkout()))")
    ExerciseSetDetailsResponse toDetailsEntity(ExerciseSet exerciseSet);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "exerciseId", target = "exercise", qualifiedByName = "mapExerciseIdToExercise")
    void updateExerciseSet(RequestExerciseSetDto requestExerciseSetDto, @MappingTarget ExerciseSet exerciseSet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    @Mapping(target = "workout", ignore = true)
    void patchExerciseSet(RequestExercisePatchDto requestExercisePatch, @MappingTarget ExerciseSet exerciseSet);

    default Exercise idToExercise(Long id) {
        return Exercise.builder().id(id).build();
    }

    @Named("exerciseToExerciseName")
    default String exerciseToExerciseName(Exercise exercise) {
        return exercise == null ? "<< не указано >>" : exercise.getName();
    }

    default String exerciseToString(Exercise exercise) {
        return (exercise == null || exercise.getMuscleGroup() == null) ?
                "<< не указано >>" :
                exercise.getMuscleGroup().getDescription();
    }

    default Long workoutToId(Workout workout) {
        return workout.getId();
    }

    @Named("mapExerciseIdToExercise")
    default Exercise mapExerciseIdToExercise(Long exerciseId) {
        return exerciseId == null ? null :
                Exercise.builder()
                        .id(exerciseId)
                        .build();
    }
}
