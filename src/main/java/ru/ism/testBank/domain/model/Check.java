package ru.ism.testBank.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checklist", uniqueConstraints = {
        @UniqueConstraint(name = "unique_habit_and_date", columnNames = {"habit_id", "date"})})

public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;
    @Column(name = "date", unique = true, nullable = false)
    private LocalDate date;
}
