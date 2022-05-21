import model.*;
import service.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        TaskManager fileTaskManager = Managers.getDefaultDB();
      //  System.out.println("записи из файла");
       // Managers.getDefault().printAllTask();
      //  System.out.println("история из файла");
      //  System.out.println(Managers.getDefaultHistory().getHistory());
       // addTestObjects(fileTaskManager);
       // System.out.println("записи в история локально в паияти");
       // inMemoryTaskManager.printSortByDateAllTasks();
      //  System.out.println(Managers.getDefaultHistory().getHistory());
        new HttpTaskServer().create();
        new KVServer().start();
    }

    public static void addTestObjects(TaskManager taskManager) {
        Task newTask = new Task("Task1", "1задание", Status.NEW);
        Task newTask1 = new Task("Task2", "задание2", Status.DONE);
        Epic epicTask = new Epic("epicTask1", "С", Status.DONE);
        Epic epicTask2 = new Epic("epicTask2", "С", Status.IN_PROGRESS);
        Subtask subTask = new Subtask("Яндекс subTask1", "е", Status.DONE, epicTask);
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        Subtask subTask3 = new Subtask("Яндекс subTask3", "5", Status.DONE, epicTask2);
        taskManager.updateTask(newTask, newTask.getId());
        taskManager.updateTask(newTask1, newTask1.getId());
        taskManager.updateEpic(epicTask, epicTask.getId());
        taskManager.updateEpic(epicTask2, epicTask2.getId());
        taskManager.updateSubTask(subTask, subTask.getId());
        taskManager.updateSubTask(subTask2, subTask2.getId());
        taskManager.updateSubTask(subTask3, subTask3.getId());
    }
}
