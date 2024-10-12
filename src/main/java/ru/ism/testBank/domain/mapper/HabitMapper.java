package ru.ism.testBank.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ism.testBank.domain.dto.HabitInDto;
import ru.ism.testBank.domain.dto.HabitOutDto;
import ru.ism.testBank.domain.dto.HabitPatchDto;
import ru.ism.testBank.domain.model.Habit;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface HabitMapper {
    Habit dtoToModel(HabitInDto habitInDto);
    HabitOutDto modelToDto(Habit habit);
    Habit patchToModel(HabitPatchDto habitPatchDto);
    @Mapping(source = "habitId", target = "id")
    Habit longToHabit(Long habitId);

}
