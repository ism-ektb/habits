package ru.ism.testBank.domain.mapper;

import org.springframework.stereotype.Component;
import ru.ism.testBank.domain.model.Habit;

@Component
public class HabitPatcher {

    public Habit patch(Habit habit, Habit patch){
        if (patch.getTitle() != null) habit.setTitle(patch.getTitle());
        if (patch.getDescription() != null) habit.setDescription(patch.getDescription());
        if (patch.getPeriod() != null) habit.setPeriod(patch.getPeriod());
        return habit;
    }
}
