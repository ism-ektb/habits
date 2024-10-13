package ru.ism.testBank.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ism.testBank.domain.dto.CountDto;
import ru.ism.testBank.domain.model.Check;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий хранения информации о выполнении привычек
 */
@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {

    @Query("SELECT c FROM Check AS c " +
            "WHERE c.habit.user.id = ?1 " +
            "AND (c.date BETWEEN ?2 AND ?3) " +
            "AND ((c.habit.id = ?4) OR (?4 IS NULL )) " +
            "ORDER BY c.date")
    List<Check> findCheckByParam(Long userId, LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest);

    List<Check> findChecksByDateAndAndHabitId(LocalDate date, Long habitId);

    /**
     * Подсчет текущих серий выполнения привычек (streak).
     */
    @Query(value = "SELECT COUNT(*) AS con, " +
            "       MIN(groups.d) AS minDate " +
            "FROM ( SELECT ROW_NUMBER() OVER (ORDER BY c.date)                     AS rn, " +
            "    CAST(c.date - make_interval(0, 0, 0," +
            "    CAST((ROW_NUMBER() OVER (ORDER BY c.date)) AS integer)) AS DATE) AS grp, " +
            "    c.date                                                           AS d " +
            "      FROM checklist as c " +
            "      WHERE (c.date BETWEEN ?1 AND ?2) " +
            "            AND (c.habit_id = ?3)) " +
            "         AS groups " +
            "GROUP BY grp", nativeQuery = true)
    List<CountDto> stat(LocalDate startRange, LocalDate finishRange, Long habitId, PageRequest pageRequest);

}
