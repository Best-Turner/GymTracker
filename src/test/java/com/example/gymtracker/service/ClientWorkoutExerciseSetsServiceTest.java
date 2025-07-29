package com.example.gymtracker.service;

import com.example.gymtracker.dto.request.RequestExerciseSetDto;
import com.example.gymtracker.dto.response.ExerciseSetDetailsResponse;
import com.example.gymtracker.dto.response.ExerciseSetShortResponse;
import com.example.gymtracker.exception.customException.ExerciseSetNotFoundException;
import com.example.gymtracker.mapper.ExerciseSetMapper;
import com.example.gymtracker.model.*;
import com.example.gymtracker.repository.ExerciseSetRepository;
import com.example.gymtracker.repository.WorkoutRepository;
import com.example.gymtracker.service.impl.ClientWorkoutExerciseSetsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientWorkoutExerciseSetsServiceTest {

    @Mock
    private ExerciseSetRepository exerciseSetRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private ExerciseSetMapper mapper;

    @InjectMocks
    ClientWorkoutExerciseSetsServiceImpl service;

    private final Long CLIENT_ID = 1L;
    private final Long WORKOUT_ID = 1L;
    private final Long EXERCISE_SET_ID = 1L;
    private ExerciseSet exerciseSet;
    private ExerciseSetShortResponse shortResponse;
    private ExerciseSetDetailsResponse detailsResponse;
    private final Client client = Client.builder()
            .id(CLIENT_ID)
            .name("test")
            .gender(Gender.FEMALE)
            .build();
    private final Workout workout = Workout.builder()
            .id(WORKOUT_ID)
            .client(client)
            .build();


    @BeforeEach
    void setUp() {
        exerciseSet = ExerciseSet.builder()
                .workout(workout)
                .weight(55.0)
                .reps(10)
                .build();
        shortResponse = new ExerciseSetShortResponse(1L, 55.0, 10);
        detailsResponse =
                new ExerciseSetDetailsResponse(1L,
                        55.0, 10,
                        "Спина", MuscleGroup.BACK.getDescription(),
                        WORKOUT_ID);
    }

    @Test
    void whenGetExerciseSetByClientIdAndWorkoutIdThenReturnListExerciseSetShortResponse() {
        //Arrange
        when(exerciseSetRepository.findByWorkoutIdAndWorkoutClientId(WORKOUT_ID, CLIENT_ID))
                .thenReturn(List.of(exerciseSet));
        when(mapper.toDto(exerciseSet)).thenReturn(shortResponse);
        //Act
        List<ExerciseSetShortResponse> result = service.clientExerciseSets(CLIENT_ID, WORKOUT_ID);
        //Assert
        verify(exerciseSetRepository).findByWorkoutIdAndWorkoutClientId(WORKOUT_ID, CLIENT_ID);
        assertNotNull(result);
        assertEquals(List.of(shortResponse), result);
    }

    @Test
    void whenGetExerciseSetByExerciseSetIdThenReturnExerciseSetDetailsResponse() {
        //Arrange
        when(exerciseSetRepository.findById(EXERCISE_SET_ID))
                .thenReturn(Optional.of(exerciseSet));
        when(mapper.toDetailsEntity(exerciseSet)).thenReturn(detailsResponse);
        //Act
        ExerciseSetDetailsResponse result = service.exerciseSetById(CLIENT_ID, WORKOUT_ID, EXERCISE_SET_ID);
        //Assert
        verify(exerciseSetRepository).findById(EXERCISE_SET_ID);
        assertNotNull(result);
        assertEquals(detailsResponse, result);
    }

    @Test
    void whenDeleteWithValidId() {
        //Arrange
        when(exerciseSetRepository.findById(EXERCISE_SET_ID))
                .thenReturn(Optional.of(exerciseSet));
        //Act
        service.delete(CLIENT_ID, WORKOUT_ID, EXERCISE_SET_ID);
        //Assert
        verify(exerciseSetRepository).delete(exerciseSet);
    }


    @Test
    void whenDeleteWithInvalidId() {
        //Arrange
        when(exerciseSetRepository.findById(EXERCISE_SET_ID))
                .thenReturn(Optional.empty());
        //Act
        assertThrows(ExerciseSetNotFoundException.class,
                () -> service.delete(CLIENT_ID, WORKOUT_ID, EXERCISE_SET_ID));
        //Assert
        verify(exerciseSetRepository, never()).delete(any());
    }


    @Test
    void whenCreateExerciseSetThenReturnExerciseSetShortResponse() {
        //Arrange
        when(workoutRepository.getWorkoutByClientIdAndId(CLIENT_ID, WORKOUT_ID)).thenReturn(workout);
        when(mapper.toEntity(any())).thenReturn(exerciseSet);
        when(exerciseSetRepository.save(exerciseSet)).thenReturn(exerciseSet);
        when(mapper.toDto(exerciseSet)).thenReturn(shortResponse);
        //Act
        ExerciseSetShortResponse result = service.createExerciseSet(CLIENT_ID, WORKOUT_ID,
                new RequestExerciseSetDto(55.0, 10, null));
        //Assert
        verify(workoutRepository).getWorkoutByClientIdAndId(CLIENT_ID, WORKOUT_ID);
        verify(mapper).toEntity(any());
        verify(exerciseSetRepository).save(exerciseSet);
        assertNotNull(result);
        assertEquals(shortResponse, result);
    }

    @Test
    void whenUpdateWithValidExerciseSetId() {
        //Arrange
        when(exerciseSetRepository.findById(EXERCISE_SET_ID))
                .thenReturn(Optional.of(exerciseSet));
        doNothing().when(mapper).updateExerciseSet(any(), any());
        when(exerciseSetRepository.save(exerciseSet)).thenReturn(exerciseSet);
        //Act
        service.updateExerciseSet(CLIENT_ID, WORKOUT_ID, EXERCISE_SET_ID,
                new RequestExerciseSetDto(55.0, 10, null));

        //Assert
        verify(mapper).updateExerciseSet(any(), any());
        verify(exerciseSetRepository).save(exerciseSet);
    }



    @Test
    void patchExerciseSetShouldUpdateWeightWhenWeightFieldProvided() {
        // Arrange
        Map<String, Object> updates = Map.of("weight", 55.5);
        when(exerciseSetRepository.findById(EXERCISE_SET_ID))
                .thenReturn(Optional.of(exerciseSet));
        when(exerciseSetRepository.save(exerciseSet)).thenReturn(exerciseSet);

        // Act
        service.patchExerciseSet(CLIENT_ID, WORKOUT_ID, EXERCISE_SET_ID, updates);

        // Assert
        assertEquals(55.5, exerciseSet.getWeight());
        assertEquals(10, exerciseSet.getReps());
        verify(exerciseSetRepository).save(exerciseSet);
    }
}