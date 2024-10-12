package ru.ism.testBank.service;

import org.springframework.data.domain.PageRequest;
import ru.ism.testBank.domain.dto.HabitInDto;
import ru.ism.testBank.domain.model.Habit;
import ru.ism.testBank.domain.model.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HabitService {

    Habit create(Habit habit);
    Habit patch(Habit patch, Long habitId);
    void delete(Long id);
    List<Habit> get(LocalDateTime startSearch, LocalDateTime finishSearch, Period period, PageRequest pageRequest);
    Habit getHabitById(Long Id);
}
