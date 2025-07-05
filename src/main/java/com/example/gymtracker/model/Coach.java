package com.example.gymtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coaches",
        indexes = @Index(name = "ind_coach_name",
                columnList = "name"))
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate hireDate;  // Дата приема на работу
    @Column(nullable = false)
    private boolean isActive;
    @OneToMany(mappedBy = "coach", fetch = FetchType.LAZY)
    private List<Workout> workouts = new ArrayList<>();
}
