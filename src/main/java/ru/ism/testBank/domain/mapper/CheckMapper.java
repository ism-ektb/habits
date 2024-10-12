package ru.ism.testBank.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ism.testBank.domain.dto.CheckDto;
import ru.ism.testBank.domain.model.Check;

@Mapper(componentModel = "spring", uses = HabitMapper.class)
public interface CheckMapper {

    @Mapping(source = "check.habit.id", target = "habitId")
    CheckDto modelToDto(Check check);

    @Mapping(source = "checkDto.habitId", target = "habit")
    Check dtoToModel(CheckDto checkDto);
}
