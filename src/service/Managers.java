package service;

public class Managers {
    private static TaskManager taskManagerDB;
    private static TaskManager taskManager;
    private static HistoryManager historyManager;

    public static TaskManager getDefault() {
        if (taskManager == null) {
            taskManager = new InMemoryTaskManager();
        }
        return taskManager;
    }

    public static TaskManager getDefaultDB() {
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
}
