import model.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        TaskManager fileTaskManager = Managers.getDefaultDB();
        System.out.println("записи из файла");
        Managers.getDefault().printAllTask();
        System.out.println("история из файла");
        System.out.println(Managers.getDefaultHistory().getHistory());
        // проверяем все через файлы
        addTestObjects(fileTaskManager);
        // тестовый код для изменния с доступом по id так как непределяем что саб что эпик а что таск
//        System.out.println("Меняем статусы сабтасков");
//        Subtask updateSub = new Subtask("updSUB", "С2", Status.NEW, inMemoryTaskManager.getEpic(2));
//        fileTaskManager.updateSubTask(updateSub, 4);
//        Task updateTask = new Task("updTask1", "1задание", Status.DONE);
//        fileTaskManager.updateTask(updateTask, 0);
//        Epic epicTask = new Epic("updEpic1", "Сjj", Status.DONE);
//        fileTaskManager.updateEpic(epicTask, 3);
//        fileTaskManager.printAllTask();
//        System.out.println("Теперь удаление");
//        fileTaskManager.removeEpic(2);
//        fileTaskManager.removeTask(0);
//        fileTaskManager.printAllTask();
        System.out.println("записи в история локально в паияти");
        inMemoryTaskManager.printSortByDateAllTasks();
        System.out.println(Managers.getDefaultHistory().getHistory());
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
