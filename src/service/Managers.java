package service;

public class Managers {
    private static FileBackedTasksManager taskManagerDB;
    private static HttpTaskManager taskManagerHTTP;
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
            taskManagerDB = new FileBackedTasksManager("db.csv");
        }
        return taskManagerDB;
    }

    public static HttpTaskManager getDefaultHTTP() {
        if (taskManagerHTTP == null) {
            taskManagerHTTP = new HttpTaskManager("http://localhost:8078");
        }
        return taskManagerHTTP;
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
