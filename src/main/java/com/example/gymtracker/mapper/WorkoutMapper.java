package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.*;
import com.example.gymtracker.exception.customException.ValueCastException;
import com.example.gymtracker.model.*;
import org.mapstruct.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkoutMapper extends EntityMapper<RequestWorkoutDto, Workout, ResponseWorkoutDto> {


    @Override
    @Mapping(source = "type", target = "type", qualifiedByName = "typeToString")
    @Mapping(source = "coach", target = "coachId", qualifiedByName = "coachToId")
    ResponseWorkoutDto toDto(Workout workout);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    @Mapping(target = "exerciseSets", ignore = true)
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToType")
    @Mapping(target = "coach", source = "coachId", qualifiedByName = "idToCoach")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    Workout toEntity(RequestWorkoutDto requestWorkoutDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercises", ignore = true)
    @Mapping(target = "exerciseSets", ignore = true)
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToType")
    @Mapping(target = "coach", source = "coachId", qualifiedByName = "idToCoach")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    void updateWorkout(RequestWorkoutDto sourceWorkout, @MappingTarget Workout workout);

    @Mapping(target = "duration", source = "duration", qualifiedByName = "durationToString")
    @Mapping(target = "type", source = "type", qualifiedByName = "typeToString")
    @Mapping(target = "exercises", expression = "java(mapExercisesToShort(workout.getExercises()))")
    ResponseWorkoutWithExercise toWorkoutWithExercise(Workout workout);

    @Mapping(target = "duration", source = "duration", qualifiedByName = "durationToString")
    @Mapping(target = "type", source = "type", qualifiedByName = "typeToString")
    @Mapping(target = "exercises", expression = "java(mapExerciseToExerciseWithSets(workout))")
    ResponseWorkoutFull toWorkoutFull(Workout workout);

    @Named("idToCoach")
    default Coach idToCoach(Integer coachId) {
        Coach coach = new Coach();
        coach.setId(coachId);
        return coach;
    }

    @Named("coachToId")
    default Integer coachToId(Coach coach) {
        if (coach == null) {
            return null;
        }
        return coach.getId();
    }

    @Named("stringToType")
    default WorkoutType stringToType(String type) {
        return WorkoutType.fromDisplayName(type)
                .orElseThrow(()
                        -> new ValueCastException("Invalid workout type: " + type));
    }

    @Named("typeToString")
    default String typeToString(WorkoutType workoutType) {
        return workoutType.getDescription();
    }

    @Named("idToClient")
    default Client idToClient(Long clientId) {
        Client client = new Client();
        client.setId(clientId);
        return client;
    }

    @Named("durationToString")
    default String durationToString(Duration duration) {
        if (duration == null) {
            return null;
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    default List<ExerciseShortResponse> mapExercisesToShort(List<Exercise> exercises) {
        return exercises.stream()
                .map(ex -> new ExerciseShortResponse(
                        ex.getId(),
                        ex.getName(),
                        ex.getMuscleGroup().getDescription()))
                .toList();
    }

    default List<ExerciseWithSetsResponse> mapExerciseToExerciseWithSets(Workout workout) {
        Map<Long, List<ExerciseSet>> setsByExerciseId = workout.getExerciseSets().stream()
                .collect(Collectors.groupingBy(es -> es.getExercise().getId()));
        return workout.getExercises().stream()
                .map(exercise -> new ExerciseWithSetsResponse(
                        exercise.getId(),
                        exercise.getName(),
                        exercise.getMuscleGroup().getDescription(),
                        mapExerciseSetToShort(setsByExerciseId.get(exercise.getId()))))
                .toList();
    }

    default List<ExerciseSetShortResponse> mapExerciseSetToShort(List<ExerciseSet> exerciseSets) {
        return exerciseSets.stream()
                .map(exerciseSet -> new ExerciseSetShortResponse(
                        exerciseSet.getId(),
                        exerciseSet.getWeight(),
                        exerciseSet.getReps()))
                .toList();
    }
}
