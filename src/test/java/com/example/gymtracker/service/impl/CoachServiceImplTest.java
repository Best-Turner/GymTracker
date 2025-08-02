package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestCoachDto;
import com.example.gymtracker.dto.response.ResponseCoachDto;
import com.example.gymtracker.exception.customException.CoachNotFoundException;
import com.example.gymtracker.mapper.CoachMapper;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Specialization;
import com.example.gymtracker.repository.CoachRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoachServiceImplTest {

    private static final Integer COACH_ID = 1;
    private static final String COACH_NAME = "testCoach";
    private static final String COACH_SPECIALIZATION = "testCoach";
    private Coach coach;
    private RequestCoachDto requestCoachDto = new RequestCoachDto(COACH_NAME, COACH_SPECIALIZATION, true);
    private ResponseCoachDto responseCoachDto = new ResponseCoachDto(COACH_ID, COACH_NAME, COACH_SPECIALIZATION, LocalDate.now(), true);


    @Mock
    private CoachRepository repository;
    @Mock
    private CoachMapper mapper;

    @InjectMocks
    private CoachServiceImpl service;

    @BeforeEach
    void setUp() {
        coach = Coach.builder().id(COACH_ID).name(COACH_NAME).isActive(true).hireDate(LocalDate.now()).build();
    }

    @Test
    void getByValidIdThenReturnResponseCoachDto() {
        // Arrange
        when(repository.findById(COACH_ID)).thenReturn(Optional.of(coach));
        when(mapper.toDto(coach)).thenReturn(responseCoachDto);
        // Act
        ResponseCoachDto result = service.getById(COACH_ID);
        // Assert
        verify(repository).findById(COACH_ID);
        verify(mapper).toDto(coach);
        assertNotNull(result);
        assertEquals(responseCoachDto, result);
    }

    @Test
    void getByInvalidIdThenReturnResponseCoachDto() {
        // Arrange
        String expectedMessage = "Coach not found with id = " + COACH_ID;
        when(repository.findById(COACH_ID)).thenReturn(Optional.empty());
        // Act & Assert
        CoachNotFoundException exception = assertThrows(CoachNotFoundException.class, () -> service.getById(COACH_ID), expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getListCoachesThenReturnListCoaches() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.singletonList(coach));
        when(mapper.toDto(coach)).thenReturn(responseCoachDto);
        // Act
        List<ResponseCoachDto> result = service.getAll();
        // Assert
        verify(repository).findAll();
        verify(mapper).toDto(coach);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseCoachDto, result.get(0));
    }


    @Test
    void whenSaveNewCoachThenReturnResponseCoachDto() {
        // Arrange
        when(mapper.toEntity(requestCoachDto)).thenReturn(coach);
        when(mapper.toDto(coach)).thenReturn(responseCoachDto);
        when(repository.save(coach)).thenReturn(coach);
        // Act
        ResponseCoachDto result = service.save(requestCoachDto);
        // Assert
        verify(repository).save(coach);
        verify(mapper).toEntity(requestCoachDto);
        verify(mapper).toDto(coach);
        assertNotNull(result);
        assertEquals(responseCoachDto, result);

    }

    @Test
    void whenDeleteCoachByValidId() {
        // Arrange
        when(repository.findById(COACH_ID)).thenReturn(Optional.of(coach));
        // Act
        service.deleteById(COACH_ID);
        // Assert
        verify(repository).delete(coach);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void whenDeleteCoachByInvalidId() {
        // Arrange
        String expectedMessage = "Coach not found with id = " + COACH_ID;
        when(repository.findById(COACH_ID)).thenReturn(Optional.empty());
        // Act & Assert
        CoachNotFoundException exception = assertThrows(CoachNotFoundException.class, () -> service.deleteById(COACH_ID));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void whenUpdateCoachWithValidIdThenReturnResponseCoachDto() {
        // Arrange
        RequestCoachDto updatedCoach = new RequestCoachDto("newName", "Выносливость", false);
        when(repository.findById(COACH_ID)).thenReturn(Optional.of(coach));
        when(repository.save(coach)).thenReturn(Coach.builder().name("newName").specialization(Specialization.ENDURANCE).isActive(true).build());
        doNothing().when(mapper).update(updatedCoach, coach);
        // Act
        ResponseCoachDto newCoach = service.update(COACH_ID, updatedCoach);
        // Assert
        verify(repository).findById(COACH_ID);
        verify(repository).save(any());
    }

    @Test
    void whenGetCoachByValidIdThenReturnResponseCoachDto() {
        // Arrange
        when(repository.findById(COACH_ID)).thenReturn(Optional.of(coach));

        // Act
        Coach result = service.getCoachById(COACH_ID);
        // Assert
        verify(repository).findById(COACH_ID);
        assertNotNull(result);
        assertEquals(coach, result);
    }

    @Test
    void whenIsExistsCoachIdThenReturnTrue() {
        // Arrange
        when(repository.existsById(COACH_ID)).thenReturn(true);

        // Act
        boolean result = service.isExist(COACH_ID);
        // Assert
        verify(repository).existsById(COACH_ID);
        assertTrue(result);
    }
}