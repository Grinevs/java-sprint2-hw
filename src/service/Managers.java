package service;

public class Managers {
    public static TaskManager getDefault() {
        TaskManager inMemoryTaskManager=new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory () {
        HistoryManager history = new InMemoryHistoryManager();
        return history;
    }
}
