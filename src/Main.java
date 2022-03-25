import model.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        addTestObjects(inMemoryTaskManager);
        System.out.println(Managers.getDefaultHistory().getHistory());
        inMemoryTaskManager.printAllTask();
        System.out.println("Меняем статусы сабтасков");
        Subtask updateSub = new Subtask("updSUB", "С2", Status.NEW, inMemoryTaskManager.getEpic(2));
        inMemoryTaskManager.updateSubTask(updateSub, 4);
        Task updateTask = new Task("updTask1", "1задание", Status.DONE);
        inMemoryTaskManager.updateTask(updateTask, 0);
        Epic epicTask = new Epic("updEpic1", "С", Status.DONE);
        inMemoryTaskManager.updateEpic(epicTask, 3);
        inMemoryTaskManager.printAllTask();
        System.out.println("Теперь удаление");
        inMemoryTaskManager.removeEpic(2);
        inMemoryTaskManager.removeTask(0);
        inMemoryTaskManager.printAllTask();
        System.out.println("история");
        System.out.println(Managers.getDefaultHistory().getHistory());
    }

    public static void addTestObjects(TaskManager inMemoryTaskManager) {
        Task newTask = new Task("Task1", "1задание", Status.NEW);
        Task newTask1 = new Task("Task2", "задание2", Status.DONE);
        Epic epicTask = new Epic("epicTask1", "С", Status.DONE);
        Epic epicTask2 = new Epic("epicTask2", "С", Status.IN_PROGRESS);
        Subtask subTask = new Subtask("Яндекс subTask1", "е", Status.DONE, epicTask);
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        Subtask subTask3 = new Subtask("Яндекс subTask3", "5", Status.DONE, epicTask2);
        inMemoryTaskManager.getTaskMap().put(newTask.getId(), newTask);
        inMemoryTaskManager.getTaskMap().put(newTask1.getId(), newTask1);
        inMemoryTaskManager.getEpicMap().put(epicTask.getId(), epicTask);
        inMemoryTaskManager.getEpicMap().put(epicTask2.getId(), epicTask2);
        inMemoryTaskManager.getSubTaskMap().put(subTask.getId(), subTask);
        inMemoryTaskManager.getSubTaskMap().put(subTask2.getId(), subTask2);
        inMemoryTaskManager.getSubTaskMap().put(subTask3.getId(), subTask3);
        Managers.getDefault().getTask(0);
        Managers.getDefault().getTask(1);
        Managers.getDefault().getEpic(2);
        Managers.getDefault().getEpic(3);
        Managers.getDefault().getTask(0);
        Managers.getDefault().getTask(1);
        Managers.getDefault().getEpic(2);
        Managers.getDefault().getEpic(3);
        Managers.getDefault().getSubTask(4);
        Managers.getDefault().getSubTask(5);
        Managers.getDefault().getSubTask(6);
        Managers.getDefault().getSubTask(4);
        Managers.getDefault().getSubTask(5);
        Managers.getDefault().getSubTask(6);
    }
}
