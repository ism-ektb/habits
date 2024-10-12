package ru.ism.testBank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ism.testBank.domain.mapper.HabitPatcher;
import ru.ism.testBank.domain.model.Habit;
import ru.ism.testBank.domain.model.Period;
import ru.ism.testBank.domain.model.User;
import ru.ism.testBank.exception.exception.BaseRelationshipException;
import ru.ism.testBank.exception.exception.NoFoundObjectException;
import ru.ism.testBank.repository.HabitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository repository;
    private final UserService userService;
    private final HabitPatcher habitPatcher;

    @Override
    public Habit create(Habit habit) {
        User user = userService.getCurrentUser();
        habit.setUser(user);
        return repository.save(habit);
    }

    @Override
    public Habit patch(Habit patch, Long habitId) {
        Habit habit = repository.findById(habitId).orElseThrow(()
                -> new NoFoundObjectException(String.format("Привычки с Id '%s' + не существует", habitId)));
        if (userService.getCurrentUser().getId() != habit.getUser().getId()) throw new BaseRelationshipException(
                String.format("Привычка с id '%s' не принадлежит текущему пользователу", habitId));
        habit = habitPatcher.patch(habit, patch);

        return repository.save(habit);
    }

    @Override
    public void delete(Long habitId) {
        Habit habit = repository.findById(habitId).orElseThrow(()
                -> new NoFoundObjectException(String.format("Привычки с Id '%s' + не существует", habitId)));
        if (userService.getCurrentUser().getId() != habit.getUser().getId()) throw new BaseRelationshipException(
                String.format("Привычка с id '%s' не принадлежит текущему пользователу, удаление не возможно", habitId));
        repository.delete(habit);
    }

    @Override
    public List<Habit> get(LocalDateTime startSearch, LocalDateTime finishSearch, Period period, PageRequest pageRequest) {
        Long userId = userService.getCurrentUser().getId();

        return repository.findHabitByParam(userId, startSearch, finishSearch, period, pageRequest);
    }

    @Override
    public Habit getHabitById(Long habitId) {
        return repository.findById(habitId).orElseThrow(() -> new NoFoundObjectException(
                String.format("Привычки с Id '%s' + не существует", habitId)));
    }
}
