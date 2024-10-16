import module.Habit;
import module.Series;
import module.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckTest {

    UserService userService = new UserService();

    @BeforeEach
    void setUp() {
        User user1 = new User("Иван", "2@2.2", "1234");
        userService.create(user1);
        User user2 = new User("Петр", "3@3.3", "4567");
        userService.create(user2);
        Habit habit = new Habit("петь", "петь в хоре");
        Habit createHabit = userService.createHabit(1L, habit);
        Habit habit2 = new Habit("танцевать", "танцевать в хоре");
        Habit createHabit2 = userService.createHabit(1L, habit2);
    }

    @Test
    public void createCheck() {
        Habit habit = userService.addCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 9)));
        System.out.println(habit.getCheckSet());
        assertEquals(habit.getCheckSet().size(), 4);
    }

    @Test
    public void testDeleteCheck() {
        userService.addCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 9)));
        Habit habit = userService.deleteCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2)));
        System.out.println(habit.getCheckSet());
        assertEquals(habit.getCheckSet().size(), 2);
    }

    @Test
    public void testFindCheckByParam() {
        userService.addCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 9)));
        List<LocalDate> result = userService.findCheckByParam(1L, 1L, null, null);
        System.out.println(result);
        assertEquals(result.size(), 4);

        List<LocalDate> result1 = userService.findCheckByParam(1L, 1L,
                LocalDate.of(2024, 10, 4),
                LocalDate.of(2024, 10, 9));
        System.out.println(result1);
        assertEquals(result1.size(), 2);

    }

    @Test
    public void testPercentCheck() {
        userService.addCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 9)));
        Long result = userService.percentCheck(1L, 1L,
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 10));
        assertEquals(result, 100L);

        Long result1 = userService.percentCheck(1L, 1L,
                LocalDate.of(2024, 10, 5),
                LocalDate.of(2024, 10, 10));
        assertEquals(result1, 50L);
    }

    /**
     * Подсчет текущих серий выполнения привычек (streak).
     */
    @Test
    public void testFindSeries() {
        userService.addCheck(1L, 1L, List.of(LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 2),
                LocalDate.of(2024, 10, 12),
                LocalDate.of(2024, 10, 8),
                LocalDate.of(2024, 10, 9)));
        List<Series> result = userService.findSeries(1L, 1L, null, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(1).getSizeOfSeries(), 3);
        assertEquals(result.get(1).getStartSeries(), LocalDate.of(2024, 10, 8));
    }
}

