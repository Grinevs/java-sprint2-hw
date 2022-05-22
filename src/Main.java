import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import server.HttpTaskServer;
import server.KVServer;
import service.Managers;
import service.TaskManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager fileTaskManager = Managers.getDefaultDB();
        addTestObjects(fileTaskManager);
        new HttpTaskServer().create();
        KVServer kvs = new KVServer();
        kvs.start();
        kvs.loadStartDataFromFile();
        TaskManager httpTaskManager = Managers.getDefaultHTTP();
    }

    public static void addTestObjects(TaskManager taskManager) {
        Task newTask = new Task("Task1", "1задание", Status.NEW);
        taskManager.updateTask(newTask, newTask.getId());
        Task newTask1 = new Task("Task2", "задание2", Status.DONE);
        taskManager.updateTask(newTask1, newTask1.getId());
        Epic epicTask = new Epic("epicTask1", "С", Status.DONE);
        taskManager.updateEpic(epicTask, epicTask.getId());
        Epic epicTask2 = new Epic("epicTask2", "С", Status.IN_PROGRESS);
        taskManager.updateEpic(epicTask2, epicTask2.getId());
        Subtask subTask = new Subtask("Яндекс subTask1", "е", Status.DONE, epicTask);
        taskManager.updateSubTask(subTask, subTask.getId());
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        taskManager.updateSubTask(subTask2, subTask2.getId());
        Subtask subTask3 = new Subtask("Яндекс subTask3", "5", Status.DONE, epicTask2);
        taskManager.updateSubTask(subTask3, subTask3.getId());
    }
}
