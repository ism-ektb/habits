package ru.ism.testBank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.ism.testBank.domain.model.Check;
import ru.ism.testBank.domain.model.Count;
import ru.ism.testBank.domain.model.Habit;
import ru.ism.testBank.domain.model.User;
import ru.ism.testBank.exception.exception.BaseRelationshipException;
import ru.ism.testBank.exception.exception.NoFoundObjectException;
import ru.ism.testBank.repository.CheckRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService{
    private final CheckRepository repository;
    private final UserService userService;
    private final HabitService habitService;

    @Override
    public Check create(Check check) {
        Habit habit = habitService.getHabitById(check.getHabit().getId());
        User user = userService.getCurrentUser();
        if (user.getId() != habit.getUser().getId()) throw new BaseRelationshipException(
                String.format("Попытка отметки чужой привычки с Id '%s'", check.getHabit().getId()));
        return repository.save(check);
    }

    @Override
    public void delete(Check check) {
        Habit habit = habitService.getHabitById(check.getHabit().getId());
        User user = userService.getCurrentUser();
        if (user.getId() != habit.getUser().getId()) throw new BaseRelationshipException(
                String.format("Попытка удаления отметки чужой привычки с Id '%s'", check.getHabit().getId()));
        List<Check> checks = repository.findChecksByDateAndAndHabitId(check.getDate(), check.getHabit().getId());
        if (checks.isEmpty()) {
            throw new NoFoundObjectException(String.format("Отметки '%s' не существует", check.toString()));
        }
        repository.delete(checks.get(0));


    }

    @Override
    public List<Check> findCheck(LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest) {
        User user = userService.getCurrentUser();
        return repository.findCheckByParam(user.getId(), startRange, finishRange, habitId, pageRequest);
    }

    @Override
    public List<Count> getStat() {
        return repository.stat();
    }
}
