package ru.ism.testBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ism.testBank.annotation.startBeforEnd.ValidStartIsBeforeEnd;
import ru.ism.testBank.domain.dto.CheckDto;
import ru.ism.testBank.domain.mapper.CheckListMapper;
import ru.ism.testBank.domain.mapper.CheckMapper;
import ru.ism.testBank.domain.model.Check;
import ru.ism.testBank.domain.dto.CountDto;
import ru.ism.testBank.service.CheckService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/check")
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с информацией о выполнении привычек", description = "Доступен только авторизованным пользователям")
public class CheckController {

    private final CheckService service;
    private final CheckMapper mapper;
    private final CheckListMapper listMapper;

    @PostMapping
    @Operation(summary = "добавление информации о выполнении привычки")
    public CheckDto create(@RequestBody @Valid CheckDto checkDto) {
        Check check = mapper.dtoToModel(checkDto);
        check = service.create(check);
        log.info("Сохранена информация о выполнении привычки {}, {}", check.getHabit().getId(), check.getDate());
        return mapper.modelToDto(check);
    }

    @DeleteMapping
    @Operation(summary = "удаление информации о выполнении привычки")
    public void deleteCheck(@RequestBody @Valid CheckDto checkDto) {
        Check check = mapper.dtoToModel(checkDto);
        service.delete(check);
        log.info("Удалена информация о выполнении привычки {}, {}", check.getHabit().getTitle(), check.getDate());
    }

    @GetMapping
    @ValidStartIsBeforeEnd
    @Operation(summary = "Получение списка выполненных привычек")
    public List<CheckDto> getChecks(@RequestParam(required = false,
            defaultValue = "#{T(java.time.LocalDate).now().minusYears(200)}") LocalDate startRange,
                                    @RequestParam(required = false,
                                            defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate finishRange,
                                    @RequestParam(required = false) Long habitId,
                                    @RequestParam(defaultValue = "0", required = false) int from,
                                    @RequestParam(defaultValue = "10", required = false) int size) {
        List<Check> checks = service.findCheck(startRange, finishRange, habitId, PageRequest.of(from / size, size));
        return listMapper.modelsToDtos(checks);
    }

    @GetMapping("/stat")
    @Operation(summary = "Подсчет текущих серий выполнения привычек (streak).")
    public List<CountDto> stat(@RequestParam(required = false,
            defaultValue = "#{T(java.time.LocalDate).now().minusYears(200)}") LocalDate startRange,
                               @RequestParam(required = false,
                                       defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate finishRange,
                               @RequestParam Long habitId,
                               @RequestParam(defaultValue = "0", required = false) int from,
                               @RequestParam(defaultValue = "10", required = false) int size){
        return service.getStat(startRange, finishRange, habitId, PageRequest.of(from / size, size));
    }

    @GetMapping("/percent")
    @Operation(summary = "Процент успешного выполнения привычек за определенный период.")
    public Integer stat(@RequestParam LocalDate startRange,
                               @RequestParam LocalDate finishRange,
                               @RequestParam Long habitId){
        return service.getPercent(startRange, finishRange, habitId);
    }

}
