package wallet.storage;

import wallet.model.record.Expense;
import wallet.model.task.Task;

import java.io.File;
import java.util.ArrayList;

public class StorageManager {
    public static final String MESSAGE_ERROR_MKDIR = "Error when trying to create directory.";
    public static final String DEFAULT_STORAGE_DIRECTORY = "./data";

    private TaskStorage taskStorage;
    private ExpenseStorage expenseStorage;

    /**
     * Constructs a new StorageManager object with all storage classes.
     */
    public StorageManager() {
        createDir();
        this.taskStorage = new TaskStorage();
        this.expenseStorage = new ExpenseStorage();
    }

    /**
     * Creates the directory for storing data if it does not exist.
     */
    public void createDir() {
        File file = new File(DEFAULT_STORAGE_DIRECTORY);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (SecurityException se) {
                System.out.println(MESSAGE_ERROR_MKDIR);
            }
        }
    }

    public ArrayList<Task> loadTask() {
        return taskStorage.loadFile();
    }

    public void addTask(Task task) {
        taskStorage.writeToFile(task);
    }

    public void deleteTask(ArrayList<Task> taskList, int index) {
        taskStorage.removeFromFile(taskList, index);
    }

    public ArrayList<Expense> loadExpense() {
        return expenseStorage.loadFile();
    }

    public void addExpense(Expense expense) {
        expenseStorage.writeToFile(expense);
    }
}
