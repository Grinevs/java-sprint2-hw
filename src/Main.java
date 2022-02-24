public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        addTestObjects(manager);
        manager.getAllTasks();
        System.out.println("Меняем статусы сабтасков");
        Subtask updateSub = new Subtask("updSUB", "С2", Status.NEW, manager.getEpic(2));
        manager.updateSubTask(updateSub, 4);
        Task updateTask = new Task("updTask1", "1задание", Status.DONE);
        manager.updateTask(updateTask, 0);
        Epic epicTask = new Epic("updEpic1", "С", Status.DONE);
        manager.updateEpic(epicTask, 3);
        manager.getAllTasks();
        System.out.println("Теперь удаление");
        manager.removeEpic(2);
        manager.removeTask(0);
        manager.getAllTasks();
    }

    public static void addTestObjects(Manager manager) {
        Task newTask = new Task("Task1", "1задание", Status.NEW);
        Task newTask1 = new Task("Task2", "задание2", Status.DONE);
        Epic epicTask = new Epic("epicTask1", "С", Status.DONE);
        Epic epicTask2 = new Epic("epicTask2", "С", Status.IN_PROGRESS);
        Subtask subTask = new Subtask("Яндекс subTask1", "е", Status.DONE, epicTask);
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        Subtask subTask3 = new Subtask("Яндекс subTask3", "5", Status.DONE, epicTask2);
        manager.getTaskMap().put(newTask.getId(), newTask);
        manager.getTaskMap().put(newTask1.getId(), newTask1);
        manager.getEpicMap().put(epicTask.getId(), epicTask);
        manager.getEpicMap().put(epicTask2.getId(), epicTask2);
        manager.getSubTaskMap().put(subTask.getId(), subTask);
        manager.getSubTaskMap().put(subTask2.getId(), subTask2);
        manager.getSubTaskMap().put(subTask3.getId(), subTask3);
    }
}
