import module.Habit;
import module.Role;
import module.Series;
import module.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserService {
    private HashMap<Long, User> users;
    private HashMap<Long, Habit> habits;
    private Long id;

    private Long habitId;


    public UserService() {
        users = new HashMap<>();
        habits = new HashMap<>();
        id = 1L;
        habitId = 1L;
    }

    public User create(User newUser) {
        //проверка уникальности
        for (User user : users.values()) {
            if (user.getEmail().equals(newUser.getEmail())) {
                System.out.println("Email не уникален");
                return null;
            }
        }
        newUser.setId(id);
        newUser.setRole(Role.USER);
        users.put(newUser.getId(), newUser);
        return users.get(id++);
    }

    public User patch(Long userId, User patch) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("Пользователь с id " + userId + " не найден");
            return null;
        }
        if (patch.getEmail() != null) user.setEmail(patch.getEmail());
        if (patch.getName() != null) user.setName(patch.getName());
        if (patch.getPassword() != null) user.setPassword(patch.getPassword());
        return users.replace(userId, user);

    }

    public User delete(Long userId) {
        List<Habit> habitList = new ArrayList<>(findHabitByParam(userId, null, null).values());
        for (Habit habit : habitList) deleteHabit(userId, habit.getId());
        return users.remove(userId);

    }

    public Long authorization(String email, String password) {
        for (User user : users.values()) {
            if (user.getEmail() == email) {
                if (user.getPassword() == password) {
                    return user.getId();
                } else {
                    System.out.println("Неверный пароль");
                    return null;
                }
            }
        }
        System.out.println("Пользователь не найден");
        return null;
    }

    public User getUserById(Long userId) {
        return users.get(userId);
    }

    public Habit createHabit(Long userId, Habit newHabit) {
        if (getUserById(userId) == null) {
            System.out.println("Пользователя с Id " + userId + " не существует");
            return null;
        }
        for (Habit habit : habits.values()) {
            if (userId == habit.getUserId() && newHabit.getTitle().equals(habit.getTitle())) {
                System.out.println("Привычка уже существует");
                return null;
            }
        }
        newHabit.setUserId(userId);
        newHabit.setId(habitId);
        newHabit.setCreationTime(LocalDateTime.now());
        habits.put(habitId, newHabit);
        return habits.get(habitId++);
    }

    public Habit patchHabit(Long userId, Long habitId, Habit patch) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            System.out.println("Привычка с id " + habitId + " не найден");
            return null;
        }
        if (habit.getUserId() != userId) {
            System.out.println("Привычка не принадлежит пользователю");
        }
        if (patch.getTitle() != null) habit.setTitle(patch.getTitle());
        if (patch.getDescription() != null) habit.setDescription(patch.getDescription());
        return habits.replace(habitId, habit);
    }

    public Habit deleteHabit(Long userId, Long habitId) {
        List<Habit> habitList = new ArrayList<>(findHabitByParam(userId, null, null).values());
        for (Habit habit : habitList) {
            if (habit.getId() == habitId) return habits.remove(habitId);
        }
        return null;

    }

    public Habit getHabitById(Long userId, Long habitId) {
        HashMap<Long, Habit> userHabit = new HashMap<>(findHabitByParam(userId, null, null));
        return habits.get(habitId);
    }

    public HashMap<Long, Habit> findHabitByParam(Long userId, LocalDateTime startRange, LocalDateTime finishRange) {
        HashMap<Long, Habit> habitList = new HashMap<>();
        if (startRange == null) startRange = LocalDateTime.now().minusYears(200);
        if (finishRange == null) finishRange = LocalDateTime.now();
        for (Habit habit : habits.values()) {
            if ((habit.getUserId() == userId) && habit.getCreationTime().isAfter(startRange)
                    && !(habit.getCreationTime().isAfter(finishRange))) habitList.put(habit.getId(), habit);
        }
        return habitList;
    }

    public Habit addCheck(Long userId, Long habitId, List<LocalDate> checkDate) {
        Habit habit = validateHabit(userId, habitId);
        if (habit == null) return null;

        Set<LocalDate> checkSet = habit.getCheckSet();
        checkSet.addAll(checkDate);
        habit.setCheckSet(checkSet);
        habits.put(habitId, habit);
        return habits.get(habitId);
    }

    public Habit deleteCheck(Long userId, Long habitId, List<LocalDate> checkDate) {
        Habit habit = validateHabit(userId, habitId);
        if (habit == null) return null;

        Set<LocalDate> checkSet = habit.getCheckSet();
        checkSet.removeAll(checkDate);
        return habit;
    }

    public List<LocalDate> findCheckByParam(Long userId, Long habitId, LocalDate startRange,
                                            LocalDate finishRange) {
        if (startRange == null) startRange = LocalDate.now().minusYears(200);
        if (finishRange == null) finishRange = LocalDate.now();
        Habit habit = validateHabit(userId, habitId);
        if (habit == null) return null;
        Set<LocalDate> checkSet = habit.getCheckSet();
        List<LocalDate> checks = new ArrayList<>();
        for (LocalDate check : checkSet) {
            if (!(check.isBefore(startRange)) && !(check.isAfter(finishRange))) checks.add(check);
        }
        return checks;
    }

    /**
     * Процент успешного выполнения привычек за определенный период.
     */
    public Long percentCheck(Long userId, Long habitId, LocalDate startRange,
                             LocalDate finishRange) {
        List<LocalDate> checks = findCheckByParam(userId, habitId, startRange, finishRange);
        Long daySum = ChronoUnit.DAYS.between(startRange, finishRange);

        return checks.size() * 100 / (daySum + 1);
    }

    /**
     * Подсчет текущих серий выполнения привычек (streak).
     */
    public List<Series> findSeries(Long userId, Long habitId, LocalDate startRange,
                                   LocalDate finishRange) {
        List<Series> seriesList = new ArrayList<>();
        List<LocalDate> checks = findCheckByParam(userId, habitId, startRange, finishRange);
        List<LocalDate> checks2 = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (j < checks.size()) {
            if (checks.get(0).plusDays(i).isEqual(checks.get(j))) {
                checks2.add(checks.get(j).minusDays(j));
                j++;
            }
            i++;
        }

        j = 0;
        List<Long> difference = new ArrayList<>();
        while (j < checks.size()) {
            difference.add(ChronoUnit.DAYS.between(checks2.get(j), checks.get(j)));
            j++;
        }

        j = 1;
        int startSeries = 0;
        while (j < checks.size()) {
            if (!(checks2.get(j).isEqual(checks2.get(j - 1)))) {
                seriesList.add(new Series((j - startSeries), checks.get(startSeries)));
                startSeries = j;
            }
            j++;
        }
        seriesList.add(new Series((j - startSeries), checks.get(startSeries)));

        return seriesList;
    }


    private Habit validateHabit(Long userId, Long habitId) {
        Habit habit = habits.get(habitId);
        if (habit == null) {
            System.out.println("Привычка с Id " + habitId + " отсутствует");
            return null;
        }
        if (habit.getUserId() != userId) {
            System.out.println("Привычка не принадлежит пользователю");
            return null;
        }
        return habit;
    }
}
