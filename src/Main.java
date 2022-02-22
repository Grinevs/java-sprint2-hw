import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.addTestObjects();
        manager.getAllTasks("all");
        System.out.println("Меняем статусы сабтасков");
        Subtask updateSub = new Subtask("updSUB", "С", Status.NEW, (Epic) manager.getTask(2));
        manager.updateTask(updateSub, 4);
        Task updateTask = new Task("updTask1", "1задание", Status.DONE);
        manager.updateTask(updateTask, 0);
        Epic epicTask = new Epic("updEpic1", "С", Status.DONE, new ArrayList<>());
        manager.updateTask(epicTask, 3);
        manager.getAllTasks("all");
        System.out.println("Теперь удаление");
        manager.removeTask(2);
        manager.removeTask(0);
        manager.getAllTasks("all");
    }
}
