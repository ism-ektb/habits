package ru.ism.testBank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@Table(name = "habits")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    private String description;
    @Builder.Default
    private LocalDateTime start = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Period period;



}
