package service;

import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InMemoryTaskManager implements TaskManager {
    private static final Map<Integer, Task> taskMap = new HashMap<>();
    private static final Map<Integer, Epic> epicMap = new HashMap<>();
    private static final Map<Integer, Subtask> subTaskMap = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void printAllTask() {
        printTasks();
        printEpicTasks();
        printSubTasks();
    }

    @Override
    public void printTasks() {
        for (Task task : taskMap.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public void printEpicTasks() {
        for (Task epic : epicMap.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void printSubTasks() {
        for (Task subtask : subTaskMap.values()) {
            System.out.println(subtask.toString());
        }
    }

    @Override
    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    @Override
    public Map<Integer, Epic> getEpicMap() {
        return epicMap;
    }

    @Override
    public Map<Integer, Subtask> getSubTaskMap() {
        return subTaskMap;
    }

    @Override
    public Map<Integer, Task> getAllTaskMap() {
        Map<Integer, Task> tasksMap = new TreeMap<>();
        tasksMap.putAll(taskMap);
        tasksMap.putAll(epicMap);
        tasksMap.putAll(subTaskMap);
        return tasksMap;
    }

    @Override
    public void updateSubTask(Subtask subtask, int id) {
        Managers.getDefaultHistory().remove(id);
        subtask.setId(id);
        subTaskMap.put(id, subtask);
        Managers.getDefaultHistory().add(subtask);
    }

    @Override
    public void updateTask(Task task, int id) {
        Managers.getDefaultHistory().remove(id);
        task.setId(id);
        taskMap.put(id, task);
        Managers.getDefaultHistory().add(task);
    }

    @Override
    public void updateEpic(Epic epic, int id) {
            Epic oldEpic = epicMap.get(id);
        if (oldEpic != null) {
            Managers.getDefaultHistory().remove(id);
            for (int i = 0; i < oldEpic.getSubTasks().size(); i++) {
                int subId = oldEpic.getSubTasks().get(i).getId();
                removeSubTask(subId);
            }
        }
        epic.setId(id);
        epicMap.put(id, epic);
        for (int i = 0; i < epic.getSubTasks().size(); i++) {
            int subId = epic.getSubTasks().get(i).getId();
            if (!subTaskMap.containsKey(subId)) {
                subTaskMap.put(subId, epic.getSubTasks().get(i));
            }
        }
        Managers.getDefaultHistory().add(epic);
    }

    @Override
    public Task getTask(int id) {
        if (taskMap.containsKey(id)) {
            historyManager.add(taskMap.get(id));
            return taskMap.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpic(int id) {
        if (epicMap.containsKey(id)) {
            historyManager.add(epicMap.get(id));
            return epicMap.get(id);
        }
        return null;
    }

    @Override
    public Subtask getSubTask(int id) {
        if (subTaskMap.containsKey(id)) {
            historyManager.add(subTaskMap.get(id));
            return subTaskMap.get(id);
        }
        return null;
    }

    @Override
    public void removeTask(int id) {
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
            Managers.getDefaultHistory().remove(id);
        }
    }

    @Override
    public void removeEpic(int id) {
        if (epicMap.containsKey(id)) {
            List<Subtask> oldEpicTasks = epicMap.get(id).getSubTasks();
            for (int i = 0; i < oldEpicTasks.size(); i++) {
                subTaskMap.remove(oldEpicTasks.get(i).getId());
                Managers.getDefaultHistory().remove(oldEpicTasks.get(i).getId());
            }
            epicMap.remove(id);
            Managers.getDefaultHistory().remove(id);
        }
    }

    @Override
    public void removeSubTask(int id) {
        int epicId = subTaskMap.get(id).getEpicId();
        epicMap.get(epicId).removeSubTask(getSubTask(id));
        if (subTaskMap.containsKey(id)) {
            subTaskMap.remove(id);
        }
        Managers.getDefaultHistory().remove(id);
    }

    @Override
    public void removeAll() {
        taskMap.clear();
        epicMap.clear();
        subTaskMap.clear();
    }

}
