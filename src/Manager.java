import java.util.ArrayList;

public class Manager {
    private ArrayList<Task> tasks;

    public Manager() {
        this.tasks = new ArrayList<>();
    }

    public void addTestObjects() {
        Task newTask = new Task("Task1", "1задание", Status.NEW);
        Task newTask1 = new Task("Task2", "задание2", Status.DONE);
        Epic epicTask = new Epic("epicTask1", "С", Status.DONE, new ArrayList<>());
        Epic epicTask2 = new Epic("epicTask2", "С", Status.IN_PROGRESS, new ArrayList<>());
        Subtask subTask = new Subtask("Яндекс subTask1", "е", Status.DONE, epicTask);
        Subtask subTask2 = new Subtask("Яндекс subTask2", "5", Status.NEW, epicTask);
        Subtask subTask3 = new Subtask("Яндекс subTask3", "5", Status.DONE, epicTask2);
        tasks.add(newTask);
        tasks.add(newTask1);
        tasks.add(epicTask);
        tasks.add(epicTask2);
        tasks.add(subTask);
        tasks.add(subTask2);
        tasks.add(subTask3);
    }

    public void getAllTasks(String classType) {
        for (int i = 0; i < tasks.size(); i++) {
            if ((tasks.get(i).getClass().getSimpleName().equalsIgnoreCase(classType))
                    || (classType.equalsIgnoreCase("all"))) {
                System.out.println(tasks.get(i).toString());
            }
        }
    }

    public void updateTask(Task task, int id) {
        task.setId(id);
        if (task.getClass() == Epic.class) {    // добавляем сабтаски в общий лист тасокв, если они пришли с эпиком
            Epic oldEpic = (Epic) getTask(id);  // и их нет вобщем списке, а так же если эпик удален удаляем сабТаски
            for (int i = 0; i < oldEpic.getSubTasks().size(); i++) {
                if (tasks.contains(getEpicTasks(oldEpic).get(i))) {
                    tasks.remove(getEpicTasks(oldEpic).get(i));
                }
            }
            Epic epic = (Epic) task;
            for (int i = 0; i < epic.getSubTasks().size(); i++) {
                if (!tasks.contains(epic.getSubTasks().get(i))) {
                    tasks.add(epic.getSubTasks().get(i));
                }
            }
        }
        removeTask(id);
        tasks.add(task);
    }

    public Task getTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                return tasks.get(i);
            }
        }
        return null;
    }

    public ArrayList<Subtask> getEpicTasks(Epic epic) {
        return epic.getSubTasks();
    }

    public void removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                if (tasks.get(i).getClass() == Subtask.class) { // удаляем из эпика сабтаски которые переопределны
                    Subtask sub = (Subtask) tasks.get(i);
                    Epic epic = sub.getEpic();
                    epic.removeSubTask(sub);
                } else if (tasks.get(i).getClass() == Epic.class) {
                    Epic oldEpic = (Epic) tasks.get(i);
                    for (int j = 0; j < oldEpic.getSubTasks().size(); j++) { //удаляем саб которые были првязаны
                        if (tasks.contains(getEpicTasks(oldEpic).get(j))) {  // к удаленому эпику
                            tasks.remove(getEpicTasks(oldEpic).get(j));
                        }
                    }
                }
                tasks.remove(tasks.get(i));
                return;
            }
        }
    }

    public void removeAll() {
        tasks.removeAll(tasks);
    }
}
