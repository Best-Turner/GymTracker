package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.exception.customException.WorkoutNotFoundException;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Coach;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.repository.WorkoutRepository;
import com.example.gymtracker.service.ClientService;
import com.example.gymtracker.service.CoachService;
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
class WorkoutServiceImplTest {

    private static final Long WORKOUT_ID = 1L;
    private static final Long CLIENT_ID = 1L;
    private static final Integer COACH_ID = 1;

    @Mock
    private WorkoutRepository repository;
    @Mock
    private ClientService clientService;
    @Mock
    private CoachService coachService;
    @Mock
    private WorkoutMapper mapper;
    @InjectMocks
    private WorkoutServiceImpl service;

    private ResponseWorkoutDto responseWorkoutDto =
            new ResponseWorkoutDto(WORKOUT_ID, LocalDate.now(), "test", "testType", COACH_ID);
    private RequestWorkoutDto requestWorkoutDto =
            new RequestWorkoutDto(LocalDate.now(), 888L, "testType", CLIENT_ID, COACH_ID);
    private Workout workout;
    private Client client;
    private Coach coach;


    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(CLIENT_ID)
                .build();
        coach = Coach.builder()
                .id(COACH_ID)
                .build();
        workout = Workout.builder()
                .id(WORKOUT_ID)
                .client(client)
                .coach(coach)
                .build();
    }

    @Test
    void whenSaveValidWorkoutThenReturnResponseWorkoutDto() {
        //Arrange
        when(clientService.getClientById(CLIENT_ID)).thenReturn(client);
        when(mapper.toEntity(requestWorkoutDto)).thenReturn(workout);
        when(coachService.isExist(COACH_ID)).thenReturn(true);
        when(repository.save(workout)).thenReturn(workout);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);
        //Act
        ResponseWorkoutDto result = service.save(requestWorkoutDto);
        //Assert
        verify(repository).save(workout);
        assertNotNull(result);
        assertEquals(responseWorkoutDto, result);
    }


    @Test
    void whenGetWorkoutByIdThenReturnResponseWorkoutDto() {
        //Arrange
        when(repository.findById(WORKOUT_ID)).thenReturn(Optional.of(workout));
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);
        //Act
        ResponseWorkoutDto result = service.getById(WORKOUT_ID);
        //Assert
        verify(repository).findById(WORKOUT_ID);
        assertNotNull(result);
        assertEquals(responseWorkoutDto, result);
    }

    @Test
    void whenGetWorkoutWithInvalidIdThenThrowWorkoutNotFoundException() {
        //Arrange
        when(repository.findById(WORKOUT_ID)).thenReturn(Optional.empty());
        final String errorMessage = "Workout not found with id:" + WORKOUT_ID;
        //Act

        WorkoutNotFoundException workoutNotFoundException = assertThrows(WorkoutNotFoundException.class,
                () -> service.getById(WORKOUT_ID), errorMessage);
        //Assert
        verify(repository).findById(WORKOUT_ID);
        assertEquals(errorMessage, workoutNotFoundException.getMessage());
    }

    @Test
    void whenGetAllWorkoutsThenReturnListWorkouts() {
        List<Workout> expected = Collections.singletonList(workout);
        //Arrange
        when(repository.findAll()).thenReturn(expected);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);
        //Act
        List<ResponseWorkoutDto> result = service.getAll();
        //Assert
        verify(repository).findAll();
        verify(mapper).toDto(workout);
        assertEquals(1, result.size());
        assertEquals(responseWorkoutDto, result.get(0));
    }

    @Test
    void whenGetAllWorkoutsThenReturnEmptyList() {

        //Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());
        //Act
        List<ResponseWorkoutDto> result = service.getAll();
        //Assert
        verify(repository).findAll();
        assertTrue(result.isEmpty());
        verifyNoInteractions(mapper);
    }

    @Test
    void whenDeleteWithValidWorkoutId() {

        //Arrange
        when(repository.findById(WORKOUT_ID)).thenReturn(Optional.of(workout));
        doNothing().when(repository).delete(workout);
        //Act
        service.deleteById(WORKOUT_ID);
        //Assert
        verify(repository).delete(workout);
    }

    @Test
    void whenDeleteWithInvalidWorkoutId() {
        final String expectedMessage = "Workout not found with id:" + WORKOUT_ID;
        //Arrange
        when(repository.findById(WORKOUT_ID)).thenReturn(Optional.empty());
        //Act
        //Assert
        WorkoutNotFoundException exception = assertThrows(WorkoutNotFoundException.class,
                () -> service.deleteById(WORKOUT_ID), expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void update_WithValidData_ReturnsUpdatedWorkoutDto() {
        // Arrange
        RequestWorkoutDto updateDto = new RequestWorkoutDto(LocalDate.now(),
                1000L, "UPDATED_TYPE", CLIENT_ID, COACH_ID);
        when(repository.findById(WORKOUT_ID)).thenReturn(Optional.of(workout));
        when(repository.save(workout)).thenReturn(workout);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);

        // Act
        ResponseWorkoutDto result = service.update(WORKOUT_ID, updateDto);

        // Assert
        verify(repository).findById(WORKOUT_ID);
        verify(mapper).updateWorkout(updateDto, workout);
        verify(repository).save(workout);
        verify(mapper).toDto(workout);

        assertEquals(updateDto.date(), result.date());
    }

}