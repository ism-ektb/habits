package ru.ism.testBank.domain.mapper;

import org.mapstruct.Mapper;
import ru.ism.testBank.domain.dto.CheckDto;
import ru.ism.testBank.domain.model.Check;

import java.util.List;

@Mapper(componentModel = "spring", uses = CheckMapper.class)
public interface CheckListMapper {
    List<CheckDto> modelsToDtos(List<Check> checklist);
}
