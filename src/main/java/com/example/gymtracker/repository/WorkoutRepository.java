package com.example.gymtracker.repository;

import com.example.gymtracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByClientId(Long clientId);

    @Query("SELECT DISTINCT w FROM Workout w " +
           "LEFT JOIN FETCH w.exercises e " +
           "WHERE w.client.id = :clientId")
    List<Workout> findWithExercisesByClientId(@Param("clientId") Long clientId);

    @Query("SELECT DISTINCT w FROM Workout w " +
           "LEFT JOIN FETCH w.exercises e " +
           "LEFT JOIN FETCH w.exerciseSets es " +
           "WHERE w.client.id = :clientId")
    List<Workout> findWithExerciseAndExerciseSetsByClientId(@Param("clientId") Long clientId);
}
