package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestClientDto;
import com.example.gymtracker.dto.response.ResponseClientDto;
import com.example.gymtracker.service.ClientService;
import com.example.gymtracker.service.impl.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseClientDto> saveClient(@RequestBody RequestClientDto clientDto) {
        return new ResponseEntity<>(clientService.save(clientDto), HttpStatus.CREATED);
    }
}
