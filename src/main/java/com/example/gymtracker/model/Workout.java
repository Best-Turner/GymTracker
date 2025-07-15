package com.example.gymtracker.model;

import com.example.gymtracker.converter.DurationIntervalConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @Column(nullable = false)
    @Convert(converter = DurationIntervalConverter.class)
    private Duration duration;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkoutType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "workout_exercise",
            joinColumns =
            @JoinColumn(name = "workout_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "exercise_id", referencedColumnName = "id"))
    private List<Exercise> exercises = new ArrayList<>();
    @OneToMany(
            mappedBy = "workout",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ExerciseSet> exerciseSets = new ArrayList<>();
}
