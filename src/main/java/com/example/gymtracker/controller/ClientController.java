package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/clients", produces = "application/json")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ResponseClientDto>> getAllClients() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseClientDto> getClientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable("id") Long id) {
        clientService.deleteById(id);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseClientDto> saveClient(@RequestBody RequestClientDto clientDto, UriComponentsBuilder componentsBuilder) {
        ResponseClientDto savedClient = clientService.save(clientDto);
        URI location = componentsBuilder.path("/api/clients/{id}")
                .buildAndExpand(savedClient.id()).toUri();
        return ResponseEntity.created(location).body(savedClient);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseClientDto> updateClient(@PathVariable Long id, @RequestBody RequestClientDto clientDto) {
        return ResponseEntity.ok(clientService.update(id, clientDto));
    }
}
