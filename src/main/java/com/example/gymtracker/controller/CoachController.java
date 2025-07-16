package com.example.gymtracker.controller;

import com.example.gymtracker.dto.request.RequestCoachDto;
import com.example.gymtracker.dto.response.ResponseCoachDto;
import com.example.gymtracker.service.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/coaches", produces = "application/json")
@AllArgsConstructor
public class CoachController {


    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<ResponseCoachDto>> allCoaches() {
        List<ResponseCoachDto> coachDtoList = coachService.getAll();
        return coachDtoList.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(coachDtoList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCoachDto getCoachById(@PathVariable Integer id) {
        return coachService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        coachService.deleteById(id);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseCoachDto> updateCoach(@PathVariable Integer id, @RequestBody RequestCoachDto requestCoachDto) {
        return ResponseEntity.ok().body(coachService.update(id, requestCoachDto));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseCoachDto> save(@RequestBody RequestCoachDto coachDto) throws URISyntaxException {
        ResponseCoachDto savedEntity = coachService.save(coachDto);
        return ResponseEntity.created(new URI("/api/coaches/" + savedEntity.id()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(savedEntity);
    }
}