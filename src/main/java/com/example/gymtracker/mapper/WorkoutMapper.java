package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.exception.customException.ValueCastException;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.model.WorkoutType;
import org.mapstruct.*;

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
}
