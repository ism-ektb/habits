package ru.ism.testBank.service;


import org.springframework.data.domain.PageRequest;
import ru.ism.testBank.domain.model.Check;
import ru.ism.testBank.domain.model.Count;

import java.time.LocalDate;
import java.util.List;

public interface CheckService {

    Check create(Check check);

    void delete(Check check);

    List<Check> findCheck(LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest);

    List<Count> getStat();
}
