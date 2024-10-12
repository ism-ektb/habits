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
import ru.ism.testBank.domain.dto.HabitInDto;
import ru.ism.testBank.domain.dto.HabitOutDto;
import ru.ism.testBank.domain.dto.HabitPatchDto;
import ru.ism.testBank.domain.mapper.HabitListMapper;
import ru.ism.testBank.domain.mapper.HabitMapper;
import ru.ism.testBank.domain.model.Habit;
import ru.ism.testBank.domain.model.Period;
import ru.ism.testBank.service.HabitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/habits")
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с привычками", description = "Доступен только авторизованным пользователям")
public class HabitsController {
    private final HabitService service;
    private final HabitMapper mapper;
    private final HabitListMapper listMapper;


    @PostMapping
    @Operation(summary = "Добавление новой привычки")
    public HabitOutDto create(@Valid @RequestBody HabitInDto habitInDto) {
        Habit habit = mapper.dtoToModel(habitInDto);
        habit = service.create(habit);
        HabitOutDto habitOutDto = mapper.modelToDto(habit);
        log.info("Создана новая привычка с Id {}", habitOutDto);
        return mapper.modelToDto(habit);
    }

    @PatchMapping("/{habitId}")
    @Operation(summary = "Изменение данных о привычке")
    public HabitOutDto patch(@Valid @RequestBody HabitPatchDto habitPatchDto, @PathVariable Long habitId) {
        Habit patch = mapper.patchToModel(habitPatchDto);
        Habit habit = service.patch(patch, habitId);
        log.info("Пользователь {} изменил привычку с id {}", habit.getUser().getName(), habit.getId());
        return mapper.modelToDto(habit);
    }

    @DeleteMapping("/{habitId}")
    @Operation(summary = "Удаление привычки")
    public void deleteHabit(@PathVariable long habitId) {
        service.delete(habitId);
        log.info("Привычка с Id {} удалена", habitId);
    }

    @GetMapping()
    @Operation(summary = "Получение списка привычек текущего пользователя с возможностью фильтации")
    public List<HabitOutDto> getHabits(@RequestParam(required = false,
            defaultValue = "#{T(java.time.LocalDateTime).now().minusYears(200)}") LocalDateTime startSearch,
                                       @RequestParam(required = false,
                                               defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime finishSearch,
                                       @RequestParam(required = false) Period period,
                                       @RequestParam(defaultValue = "0", required = false) int from,
                                       @RequestParam(defaultValue = "10", required = false) int size) {
        List<Habit> habits = service.get(startSearch, finishSearch, period, PageRequest.of(from / size, size));
        return listMapper.modelsToDtos(habits);
    }


}
