package com.example.gymtracker.service;

import java.util.List;

public interface EntityService<K, reqDto, resDto> {

    resDto save(reqDto dto);

    resDto getById(K id);

    List<resDto> getAll();

    void deleteById(K entityId);

    resDto update(K id, reqDto updatedDto);
}
