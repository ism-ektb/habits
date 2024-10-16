import module.Habit;
import module.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HabitTest {

    UserService userService = new UserService();

    @BeforeEach
    void setUp() {
        User user1 = new User("Иван", "2@2.2", "1234");
        userService.create(user1);
        User user2 = new User("Петр", "3@3.3", "4567");
        userService.create(user2);
    }

    @Test
    public void createHabit(){
        Habit habit = new Habit("петь", "петь в хоре");
        Habit createHabit = userService.createHabit(1L, habit);
        assertEquals(createHabit.getId(), 1L);
        Habit createHabit1 = userService.createHabit(1L, habit);
        assertNull(createHabit1);
        Habit habit3 = new Habit("танцевать", "танцевать в хоре");
        Habit createHabit2 = userService.createHabit(1L, habit3);
        assertEquals(createHabit2.getId(), 2L);

    }

    @Test
    public void patchHabit(){
        Habit habit = new Habit("петь", "петь в хоре");
        userService.createHabit(1L, habit);
        Habit patch = new Habit(null, "петь в хоре по утрам");
        Habit patchResult = userService.patchHabit(1L, 1L, patch);
        assertEquals(patchResult.getDescription(), "петь в хоре по утрам");

    }

    @Test
    public void testDeleteHabit() {
        Habit habit = new Habit("петь", "петь в хоре");
        userService.createHabit(1L, habit);
        //удаление другим пользователем
        Habit deleteResult = userService.deleteHabit(2L, 1L);
        assertNull(deleteResult);
        //удаление не существующей привычки
        deleteResult = userService.deleteHabit(1L, 10L);
        assertNull(deleteResult);
        deleteResult = userService.deleteHabit(1L, 1L);
        assertEquals(deleteResult.getTitle(), "петь");
    }

    @Test
    public void testGetHabitById() {
        Habit habit = new Habit("петь", "петь в хоре");
        Habit createHabit = userService.createHabit(1L, habit);
        Habit habit2 = new Habit("танцевать", "танцевать в хоре");
        Habit createHabit2 = userService.createHabit(1L, habit2);
        Habit result = userService.getHabitById(1L, 2L);
        assertEquals(result.getTitle(), "танцевать");

    }

    @Test
    public void testFindHabitByParam() {
        Habit habit = new Habit("петь", "петь в хоре");
        Habit createHabit = userService.createHabit(1L, habit);
        Habit habit2 = new Habit("танцевать", "танцевать в хоре");
        Habit createHabit2 = userService.createHabit(1L, habit2);
        HashMap<Long, Habit> habitList = userService.findHabitByParam(1L, null, null);
        assertEquals(habitList.size(), 2);
    }
}
