package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.model.Client;

public interface ClientService extends EntityService<Long, RequestClientDto, ResponseClientDto> {

    boolean checkExists(Long clientId);

    Client getClientById(Long clientId);
}
