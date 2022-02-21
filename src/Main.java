import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.addTestObjects();
        manager.getAllTasks();
        Epic updateEpic = new Epic("Яндекс epicTest", "С", Status.NEW, new ArrayList<>());


        manager.updateTask(updateEpic, 2);
        manager.getAllTasks();
        Subtask updateSub = new Subtask("Яндекс updSUB", "С", Status.DONE, updateEpic);
        manager.updateTask(updateSub, 4);
        manager.getAllTasks();
    }


}
