package service;

import logic.Task;
import repository.TaskRepository;
import java.util.List;

public class TaskService {
    private final TaskRepository repository;

    public TaskService() {
        this.repository = new TaskRepository();
    }

    public Task createTask(String name, Task.Priority priority, String description) {
        Task task = new Task(name, priority, description);
        if (!task.isValid()) {
            throw new IllegalArgumentException("Task name is empty");
        }
        return repository.save(task);
    }

    public void deleteTask(Task task) {
        repository.delete(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }
}