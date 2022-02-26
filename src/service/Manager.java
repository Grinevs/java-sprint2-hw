package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();
    private final Map<Integer, Subtask> subTaskMap = new HashMap<>();

    public void printAllTask () {
        printTasks();
        printEpicTasks();
        printSubTasks();
    }

    public void printTasks() {
        for (Task task : taskMap.values()) {
            System.out.println(task.toString());
        }
    }

    public void printEpicTasks() {
        for (Task epic : epicMap.values()) {
            System.out.println(epic.toString());
        }
    }

    public void printSubTasks() {
        for (Task subtask : subTaskMap.values()) {
            System.out.println(subtask.toString());
        }
    }

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    public Map<Integer, Epic> getEpicMap() {
        return epicMap;
    }

    public Map<Integer, Subtask> getSubTaskMap() {
        return subTaskMap;
    }

    public void updateSubTask(Subtask subtask, int id) {
        subtask.setId(id);
        removeSubTask(id);
        subTaskMap.put(id, subtask);
    }

    public void updateTask(Task task, int id) {
        task.setId(id);
        taskMap.put(id, task);
    }

    public void updateEpic(Epic epic, int id) {
        Epic oldEpic = epicMap.get(id);
        for (int i = 0; i < oldEpic.getSubTasks().size(); i++) {
            int subId = oldEpic.getSubTasks().get(i).getId();
            removeSubTask(subId);
        }
        epic.setId(id);
        epicMap.put(id, epic);
        for (int i = 0; i < epic.getSubTasks().size(); i++) {
            int subId = epic.getSubTasks().get(i).getId();
            if (!subTaskMap.containsKey(subId)) {
                subTaskMap.put(subId, epic.getSubTasks().get(i));
            }
        }
    }

    public Task getTask(int id) {
        if (taskMap.containsKey(id)) {
            return taskMap.get(id);
        }
        return null;
    }

    public Epic getEpic(int id) {
        if (epicMap.containsKey(id)) {
            return epicMap.get(id);
        }
        return null;
    }

    public Subtask getSubTask(int id) {
        if (subTaskMap.containsKey(id)) {
            return subTaskMap.get(id);
        }
        return null;
    }

    public void removeTask(int id) {
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
        }
    }

    public void removeEpic(int id) {
        if (epicMap.containsKey(id)) {
            ArrayList<Subtask> oldEpicTasks = epicMap.get(id).getSubTasks();
            for (int i = 0; i < oldEpicTasks.size(); i++) {
                subTaskMap.remove(oldEpicTasks.get(i).getId());
            }
            epicMap.remove(id);
        }
    }

    public void removeSubTask(int id) {
        Subtask subtask = subTaskMap.get(id);
        int epicId = subtask.getEpicId();
        epicMap.get(epicId).removeSubTask(getSubTask(id));
        if (subTaskMap.containsKey(id)) {
            subTaskMap.remove(id);
        }
    }

    public void removeAll() {
        taskMap.clear();
        epicMap.clear();
        subTaskMap.clear();
    }
}
