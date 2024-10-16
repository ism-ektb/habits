package module;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Habit {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime creationTime;

    private Set<LocalDate> checkSet;

       public Habit(String title, String description) {
        this.title = title;
        this.description = description;
        checkSet = new TreeSet<>();
    }

    public Set<LocalDate> getCheckSet() {
        return checkSet;
    }

    public void setCheckSet(Set<LocalDate> checkSet) {
        this.checkSet = checkSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                ", checkSet=" + checkSet +
                '}';
    }
}
