package service;

public class Managers {
    private static FileBackedTasksManager taskManagerDB;
    private static TaskManager taskManager;
    private static HistoryManager historyManager;
    private static CounterId counterId;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            taskManager = new InMemoryTaskManager();
        }
        return taskManager;
    }

    public static FileBackedTasksManager getDefaultDB() {
        if (taskManagerDB == null) {
            taskManagerDB = new FileBackedTasksManager();
        }
        return taskManagerDB;
    }

    public static HistoryManager getDefaultHistory() {
        if (historyManager == null) {
            historyManager = new InMemoryHistoryManager();
        }
        return historyManager;
    }

    public static CounterId getCounterId() {
        if (counterId == null) {
            counterId = new CounterId(0);
        }
        return counterId;
    }
}
