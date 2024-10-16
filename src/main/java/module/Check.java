package module;

import java.time.LocalDate;

public class Check {
    private Long id;
    private Long habitId;
    private LocalDate date;

    public Check(Long habitId, LocalDate date) {
        this.habitId = habitId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
