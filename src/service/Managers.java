package service;

public class Managers {
    private static Managers instance;
    private static HistoryManager historyManager = new InMemoryHistoryManager();
    private static TaskManager taskManager = new InMemoryTaskManager();

    public static synchronized Managers getInstance() {
        if (instance == null) {
            instance = new Managers();
        }
        return instance;
    }

    public static TaskManager getDefault() {
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
