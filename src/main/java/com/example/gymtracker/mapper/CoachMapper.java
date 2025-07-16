package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestCoachDto;
import com.example.gymtracker.dto.response.ResponseCoachDto;
import com.example.gymtracker.exception.customException.ValueCastException;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Specialization;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachMapper extends EntityMapper<RequestCoachDto, Coach, ResponseCoachDto> {

    @Override
    @Mapping(target = "specialization", source = "specialization", qualifiedByName = "typeToString")
    ResponseCoachDto toDto(Coach coach);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "specialization", source = "specialization", qualifiedByName = "stringToType")
    Coach toEntity(RequestCoachDto requestCoachDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "hireDate", ignore = true)
    @Mapping(target = "specialization", source = "specialization", qualifiedByName = "stringToType")
    @Mapping(target = "active", source = "active")
    void update(RequestCoachDto updatedDto, @MappingTarget Coach forUpdate);


    @Named("typeToString")
    default String typeToString(Specialization specialization) {
        return specialization.getSpecializationName();
    }

    @Named("stringToType")
    default Specialization stringToType(String s) {
        return Specialization.fromDisplayName(s)
                .orElseThrow(() -> new ValueCastException("Invalid specialization type: " + s));
    }
}
