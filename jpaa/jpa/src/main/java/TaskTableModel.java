import logic.Task;
import service.TaskService;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

    private final List<Task> tasks;
    private final String[] columnNames = {"ID", "Task name", "Priority", "Description"};
    private final TaskService taskService;

    public TaskTableModel() {
        this.taskService = new TaskService();
        this.tasks = new ArrayList<>();

        tasks.clear();
        tasks.addAll(taskService.getAllTasks());
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return tasks.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> task.getId();
            case 1 -> task.getTaskName();
            case 2 -> task.getPriority().getDisplayName();
            case 3 -> task.getDescription();
            default -> null;
        };
    }

    public void addTask(Task task) {
        Task savedTask = taskService.createTask(
                task.getTaskName(),
                task.getPriority(),
                task.getDescription()
        );

        tasks.add(savedTask);
        int row = tasks.size() - 1;
        fireTableRowsInserted(row, row);
    }

    public void removeTask(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tasks.size()) {
            Task task = tasks.get(rowIndex);
            taskService.deleteTask(task);
            tasks.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public Task getTask(int rowIndex) {
        return (rowIndex >= 0 && rowIndex < tasks.size()) ? tasks.get(rowIndex) : null;
    }
}