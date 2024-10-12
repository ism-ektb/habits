package ru.ism.testBank.domain.mapper;

import org.mapstruct.Mapper;
import ru.ism.testBank.domain.dto.HabitOutDto;
import ru.ism.testBank.domain.model.Habit;

import java.util.List;

@Mapper(componentModel = "spring", uses = HabitMapper.class)
public interface HabitListMapper {

    List<HabitOutDto> modelsToDtos(List<Habit> Habits);
}
