package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestCoachDto;
import com.example.gymtracker.dto.response.ResponseCoachDto;
import com.example.gymtracker.model.Coach;

public interface CoachService extends EntityService<Integer, RequestCoachDto, ResponseCoachDto> {

    boolean isExist(Integer coachId);

    Coach getCoachById(Integer maybeCoachId);
}
