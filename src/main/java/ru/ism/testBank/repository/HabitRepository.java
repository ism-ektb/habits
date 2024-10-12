package ru.ism.testBank.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ism.testBank.domain.model.Habit;
import ru.ism.testBank.domain.model.Period;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {


    @Query("SELECT h FROM Habit AS h " +
            "WHERE h.user.id = ?1 " +
            "AND (h.start BETWEEN ?2 AND ?3) " +
            "AND ((h.period = ?4) OR (?4 IS NULL )) " +
            "ORDER BY h.start")
    List<Habit> findHabitByParam(Long userId, LocalDateTime startSearch, LocalDateTime finishSearch, Period period, PageRequest pageRequest);

}
