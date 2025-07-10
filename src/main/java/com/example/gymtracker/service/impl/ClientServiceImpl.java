package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.exception.ClientNotFoundException;
import com.example.gymtracker.mapper.ClientMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.service.ClientService;
import com.example.gymtracker.service.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper mapper;
    private final ClientRepository repository;

    @Override
    public ResponseClientDto save(RequestClientDto dto) {
        Client client = mapper.toClient(dto);
        return mapper.fromClient(repository.save(client));
    }

    @Override
    public ResponseClientDto getById(Long id) {
        return mapper.fromClient(getClientOrThrow(id));
    }

    @Override
    public List<ResponseClientDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromClient)
                .toList();
    }

    @Override
    public void deleteById(Long entityId) {
        getClientOrThrow(entityId);
        repository.deleteById(entityId);
    }

    @Override
    public ResponseClientDto update(Long id, RequestClientDto updatedDto) {
        Client existingClient = getClientOrThrow(id);
        mapper.updateFromClientDto(updatedDto, existingClient);
        repository.save(existingClient);
        return mapper.fromClient(existingClient);
    }

    private Client getClientOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id:" + id));
    }
}
