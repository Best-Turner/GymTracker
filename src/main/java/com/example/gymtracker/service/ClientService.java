package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;

public interface ClientService extends EntityService<Long, RequestClientDto, ResponseClientDto> {
}
