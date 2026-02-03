import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import logic.Task;

import javax.swing.*;
import java.awt.*;

/**
 * Główna aplikacja logic.Task Manager
 */
public class TaskManagerApp extends JFrame {

    private JTable taskTable;
    private TaskTableModel tableModel;
    
    private JTextField taskNameField;
    private JComboBox<Task.Priority> priorityComboBox;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JButton deleteButton;
    
    public TaskManagerApp() {
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);

        setLayout(
            new BorderLayout(10, 10)
        );

        createTablePanel();
        createInputPanel();

        ((JPanel) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        setVisible(true);
    }
    

    private void createTablePanel() {
        tableModel = new TaskTableModel();
        
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskTable.setRowHeight(25);
        taskTable.getTableHeader().setReorderingAllowed(false);

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Task list")
        );
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(
            BorderFactory.createTitledBorder("Add new task")
        );
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Task name:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        taskNameField = new JTextField(20);
        fieldsPanel.add(taskNameField, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Priority"), gbc);
        
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        priorityComboBox = new JComboBox<>(Task.Priority.values());
        priorityComboBox.setSelectedItem(Task.Priority.SREDNI);
        fieldsPanel.add(priorityComboBox, gbc);
        
        // --- Opis ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        fieldsPanel.add(new JLabel("Description:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        descriptionArea = new JTextArea(3, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        fieldsPanel.add(descScrollPane, gbc);
        
        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );
        
        addButton = new JButton("Add task");
        addButton.setBackground(new Color(76, 175, 80));

        addButton.setFocusPainted(false);
        addButton.addActionListener(
            (e)->{
                Task task = createTaskFromForm();

                if (!task.isValid()) {
                    JOptionPane.showMessageDialog(
                        TaskManagerApp.this,
                        "Tank name cant be empty!",
                        "Validation error",
                        JOptionPane.WARNING_MESSAGE
                    );
                    taskNameField.requestFocus();
                    return;
                }

                tableModel.addTask(task);

                taskNameField.setText("");
                priorityComboBox.setSelectedItem(Task.Priority.SREDNI);
                descriptionArea.setText("");
                taskNameField.requestFocus();

                JOptionPane.showMessageDialog(
                        TaskManagerApp.this,
                        "Task have been added!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        );
        
        deleteButton = new JButton("Delete selected");
        deleteButton.setBackground(new Color(244, 67, 54));

        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(
            (e)->{
                int selectedRow = taskTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(
                        TaskManagerApp.this,
                        "Select task to delete!",
                        "Selection error",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                Task task = tableModel.getTask(selectedRow);

                int confirm = JOptionPane.showConfirmDialog(
                    TaskManagerApp.this,
                    "Delete task: \"" + task.getTaskName() + "\"?",
                    "Confirmation delete",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeTask(selectedRow);
                }
            }
        );

        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);
        
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(inputPanel, BorderLayout.SOUTH);
    }
    
    private Task createTaskFromForm() {
        String taskName = taskNameField.getText().trim();
        Task.Priority priority = (Task.Priority) priorityComboBox.getSelectedItem();
        String description = descriptionArea.getText().trim();
        
        return new Task(taskName, priority, description);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TaskManagerApp app = new TaskManagerApp();
            }
        );
    }
}