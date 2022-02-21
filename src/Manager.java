import java.util.ArrayList;

public class Manager {
    private ArrayList<Task> tasks;

    public Manager() {
        this.tasks = new ArrayList<>();
    }

    public void addTestObjects() {
        Task newTask = new Task("Яндекс практикум", "Сделать задание", Status.NEW);
        Epic epicTask = new Epic("Яндекс epicTask", "С", Status.DONE, new ArrayList<>());
        Epic epicTask2 = new Epic("Яндекс epicTask2", "С", Status.IN_PROGRESS, new ArrayList<>());
        Subtask subTask = new Subtask("Яндекс subTask", "е", Status.DONE, epicTask);
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        tasks.add(newTask);
        tasks.add(epicTask);
        tasks.add(epicTask2);
        tasks.add(subTask);
        tasks.add(subTask2);
    }

    public void getAllTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i).toString());
        }

    }
    public void updateTask(Task task, int id) {
        task.setId(id);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                if (tasks.get(i).getClass() == Subtask.class) { // удаляем из эпика сабтаски которые переопределны
                    Subtask sub = (Subtask) tasks.get(i);
                    Epic epic = sub.getEpic();
                    epic.removeSubTask(sub);
                }
                tasks.remove(tasks.get(i));
                tasks.add(task);
                return;
            }
        }
        System.out.println("такого 'id = " + id +"' ненайдено");
    }

}
