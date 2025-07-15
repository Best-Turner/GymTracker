package com.example.gymtracker.mapper;

public interface EntityMapper<reqDto, entity, respDto> {

    respDto toDto(entity entity);

    entity toEntity(reqDto reqDto);
}
