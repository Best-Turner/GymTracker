package com.example.gymtracker.repository;

import com.example.gymtracker.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

    List<ExerciseSet> findByWorkoutIdAndWorkoutClientId(Long workout_id, Long workout_client_id);


}
