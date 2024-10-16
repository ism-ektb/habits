package ru.ism.testBank.service;


import org.springframework.data.domain.PageRequest;
import ru.ism.testBank.domain.model.Check;
import ru.ism.testBank.domain.dto.CountDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс работы с отметками о выполнении привычек
 */
public interface CheckService {

    Check create(Check check);

    void delete(Check check);

    /**
     * Получение списка привычек
     */
    List<Check> findCheck(LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest);

    /**
     * Подсчет текущих серий выполнения привычек (streak).
     */
    List<CountDto> getStat(LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest);

    /**
     * Процент успешного выполнения привычек за определенный период.
     */
    Integer getPercent(LocalDate startRange, LocalDate finishRange, Long habitId);
}
