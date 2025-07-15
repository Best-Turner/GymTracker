package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestCoachDto;
import com.example.gymtracker.dto.response.ResponseCoachDto;
import com.example.gymtracker.exception.customException.CoachNotFoundException;
import com.example.gymtracker.mapper.CoachMapper;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.repository.CoachRepository;
import com.example.gymtracker.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository repository;
    private final CoachMapper mapper;

    @Override
    public boolean isExist(Integer coachId) {
        return repository.existsById(coachId);
    }

    @Override
    public Coach getCoachById(Integer maybeCoachId) {
        return getOrElseThrow(maybeCoachId);
    }


    @Override
    public ResponseCoachDto save(RequestCoachDto requestCoachDto) {
        Coach entity = mapper.toEntity(requestCoachDto);
        entity.setHireDate(LocalDate.now());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public ResponseCoachDto getById(Integer id) {

        return mapper.toDto(getOrElseThrow(id));
    }

    @Override
    public List<ResponseCoachDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Integer entityId) {
        repository.delete(getOrElseThrow(entityId));
    }

    @Override
    public ResponseCoachDto update(Integer id, RequestCoachDto updatedDto) {
        Coach forUpdate = getOrElseThrow(id);
        mapper.update(updatedDto, forUpdate);
        return mapper.toDto(repository.save(forUpdate));
    }


    private Coach getOrElseThrow(Integer maybeCoachId) {
        Objects.requireNonNull(maybeCoachId, "Coach ID is not be null");
        return repository.findById(maybeCoachId)
                .orElseThrow(() -> new CoachNotFoundException("Coach not found with id = " + maybeCoachId));
    }
}
