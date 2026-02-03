package logic;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name", nullable = false, length = 100)
    private String taskName;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 20)
    private Priority priority;

    @Column(name = "description", length = 500)
    private String description;

    public enum Priority {
        NISKI("Low"),
        SREDNI("Mid"),
        WYSOKI("High"),
        KRYTYCZNY("Critical");

        private final String displayName;
        Priority(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
        @Override
        public String toString() { return displayName; }
    }

    public Task() {}

    public Task(String taskName, Priority priority, String description) {
        this.taskName = taskName;
        this.priority = priority;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isValid() {
        return taskName != null && !taskName.trim().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}