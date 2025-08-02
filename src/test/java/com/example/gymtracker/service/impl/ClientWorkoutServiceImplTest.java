package com.example.gymtracker.service.impl;

import com.example.gymtracker.dto.request.RequestWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutDto;
import com.example.gymtracker.dto.response.ResponseWorkoutFull;
import com.example.gymtracker.dto.response.ResponseWorkoutWithExercise;
import com.example.gymtracker.exception.customException.AccessDeniedException;
import com.example.gymtracker.exception.customException.ClientNotFoundException;
import com.example.gymtracker.mapper.WorkoutMapper;
import com.example.gymtracker.model.Client;
import com.example.gymtracker.model.Workout;
import com.example.gymtracker.repository.ClientRepository;
import com.example.gymtracker.repository.CoachRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientWorkoutServiceImplTest {

    private static final Long CLIENT_ID = 1L;
    private static final Long WORKOUT_ID = 1L;
    private static final LocalDate DATE = LocalDate.now();
    private static final String DURATION = "1000";
    private static final Integer COACH_ID = 1;
    private Client client;
    private Workout workout;

    private ResponseWorkoutFull responseWorkoutFull =
            new ResponseWorkoutFull(WORKOUT_ID, DATE, DURATION, "type", null);
    private ResponseWorkoutWithExercise responseWorkoutWithExercise =
            new ResponseWorkoutWithExercise(WORKOUT_ID, DATE, DURATION, "type", null);
    private ResponseWorkoutDto responseWorkoutDto =
            new ResponseWorkoutDto(WORKOUT_ID, DATE, DURATION, "type", null);
    private RequestWorkoutDto requestWorkoutDto =
            new RequestWorkoutDto(DATE, 1000L, "type", CLIENT_ID, null);

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private WorkoutMapper mapper;
    @InjectMocks
    private ClientWorkoutServiceImpl service;

    @BeforeEach
    void setUp() {
        client = Client.builder().id(CLIENT_ID).build();
        workout = Workout.builder()
                .id(WORKOUT_ID)
                .client(client)
                .build();
        client.setWorkouts(Collections.singletonList(workout));
    }

    @Test
    void clientWorkoutFullByIdWhenValidIdsReturnsWorkoutFull() {
        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.getWorkoutByClientIdAndId(CLIENT_ID, WORKOUT_ID))
                .thenReturn(workout);
        when(workoutRepository.findById(WORKOUT_ID)).thenReturn(Optional.of(workout));
        when(mapper.toWorkoutFull(workout)).thenReturn(responseWorkoutFull);

        ResponseWorkoutFull result = service.clientWorkoutFullById(CLIENT_ID, WORKOUT_ID);

        assertNotNull(result);
        verify(workoutRepository).getWorkoutByClientIdAndId(CLIENT_ID, WORKOUT_ID);
    }

    @Test
    void clientWorkoutFullByIdWhenInvalidClientThrowsException() {
        when(clientRepository.existsById(CLIENT_ID)).thenReturn(false);

        assertThrows(ClientNotFoundException.class,
                () -> service.clientWorkoutFullById(CLIENT_ID, WORKOUT_ID));
    }


    @Test
    void clientWorkoutWithExerciseByIdWhenValidIdsReturnsWorkoutWithExercises() {
        // Arrange
        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findWithExercisesByClientId(CLIENT_ID, WORKOUT_ID))
                .thenReturn(workout);
        when(workoutRepository.findById(WORKOUT_ID)).thenReturn(Optional.of(workout));
        when(mapper.toWorkoutWithExercise(workout))
                .thenReturn(responseWorkoutWithExercise);

        // Act
        ResponseWorkoutWithExercise result =
                service.clientWorkoutWithExerciseById(CLIENT_ID, WORKOUT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(responseWorkoutWithExercise, result);
        verify(workoutRepository).findWithExercisesByClientId(CLIENT_ID, WORKOUT_ID);
        verify(mapper).toWorkoutWithExercise(workout);
    }

    @Test
    void createWorkoutWithoutCoachReturnsCreatedWorkout() {

        Workout newWorkout = Workout.builder().build();

        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(client));
        when(mapper.toEntity(requestWorkoutDto)).thenReturn(newWorkout);
        when(workoutRepository.save(newWorkout)).thenReturn(workout);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);

        ResponseWorkoutDto result = service.createWorkout(CLIENT_ID, requestWorkoutDto);

        assertEquals(responseWorkoutDto, result);
        verify(coachRepository, never()).findById(any());
    }

    @Test
    void deleteClientWorkout_WhenValidIds_DeletesWorkout() {
        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findById(WORKOUT_ID))
                .thenReturn(Optional.of(workout));

        service.deleteClientWorkout(CLIENT_ID, WORKOUT_ID);

        verify(workoutRepository).deleteById(WORKOUT_ID);
    }

    @Test
    void deleteClientWorkoutWhenWorkoutNotBelongsToClientThrowsException() {
        Workout otherWorkout = Workout.builder()
                .id(2L)
                .client(Client.builder().id(999L).build())
                .build();

        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findById(WORKOUT_ID))
                .thenReturn(Optional.of(otherWorkout));

        assertThrows(AccessDeniedException.class,
                () -> service.deleteClientWorkout(CLIENT_ID, WORKOUT_ID));
    }


    @Test
    void updateWorkoutWhenValidDataReturnsUpdatedWorkout() {
        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findById(WORKOUT_ID))
                .thenReturn(Optional.of(workout));
        when(workoutRepository.save(workout)).thenReturn(workout);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);

        ResponseWorkoutDto result = service.updateWorkout(
                CLIENT_ID, WORKOUT_ID, requestWorkoutDto);

        assertEquals(responseWorkoutDto, result);
        verify(mapper).updateWorkout(requestWorkoutDto, workout);
    }

    @Test
    void patchWorkoutWhenUpdateDateReturnsPatchedWorkout() {
        Map<String, Object> updates = Map.of("date", "2023-01-01");

        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findById(WORKOUT_ID))
                .thenReturn(Optional.of(workout));
        when(workoutRepository.save(workout)).thenReturn(workout);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);

        ResponseWorkoutDto result = service.patchWorkout(
                CLIENT_ID, WORKOUT_ID, updates);

        assertEquals(LocalDate.of(2023, 1, 1), workout.getDate());
        assertEquals(responseWorkoutDto, result);
    }


    @Test
    void clientWorkoutsWhenClientExistsReturnsWorkoutsList() {
        List<Workout> workouts = List.of(workout);
        List<ResponseWorkoutDto> expected = List.of(responseWorkoutDto);

        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.findByClientId(CLIENT_ID)).thenReturn(workouts);
        when(mapper.toDto(workout)).thenReturn(responseWorkoutDto);

        List<ResponseWorkoutDto> result = service.clientWorkouts(CLIENT_ID);

        assertEquals(expected, result);
        verify(workoutRepository).findByClientId(CLIENT_ID);
    }

    @Test
    void patchWorkoutWhenInvalidFieldThrowsException() {
        Map<String, Object> updates = Map.of("invalid", "value");

        when(clientRepository.existsById(CLIENT_ID)).thenReturn(true);
        when(workoutRepository.existsById(WORKOUT_ID)).thenReturn(true);
        when(workoutRepository.findById(WORKOUT_ID))
                .thenReturn(Optional.of(workout));

        assertThrows(IllegalArgumentException.class,
                () -> service.patchWorkout(CLIENT_ID, WORKOUT_ID, updates));
    }
}