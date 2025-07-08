package com.example.gymtracker.repository;

import com.example.gymtracker.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
}
