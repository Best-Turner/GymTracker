package com.example.gymtracker.mapper;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper extends EntityMapper<RequestClientDto, Client, ResponseClientDto> {

    @Override
    ResponseClientDto toDto(Client client);

    @Override
    @Mapping(target = "workouts", ignore = true)
    Client toEntity(RequestClientDto requestClientDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    void updateFromClientDto(RequestClientDto sourceDto, @MappingTarget Client toUpdateClient);
}
