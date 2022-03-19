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

/* Синглтон для меня тут не решаает никакой задачи, если я так же создам инстанс
менеджера то получу снова null, из за порядка инициализации, или я не прав?
ЗЫ - сейчас все работает
 */
