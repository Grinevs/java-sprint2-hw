package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static final Map<Integer, Task> taskMap = new HashMap<>();
    private static final Map<Integer, Epic> epicMap = new HashMap<>();
    private static final Map<Integer, Subtask> subTaskMap = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final CounterId counterId = Managers.getCounterId();

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
    public void printSortAllTaskMap() {
        getAllTaskMap().forEach((integer, task) -> System.out.println(task.toString()));
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        Comparator<Task> comp = Comparator.nullsLast(Comparator.comparing(Task::getStartTime))
                .thenComparing(Task::getId);
        Set<Task> tasksByDate = new TreeSet<>(comp);
        getAllTaskMap().forEach((integer, task) -> tasksByDate.add(task));
        return tasksByDate;
    }

    @Override
    public void printSortByDateAllTasks() {
        getPrioritizedTasks().forEach((task) -> System.out.println(task.toString()));
    }

    public boolean isTimeNotCrossing(Task task) {
        for (Task t : getPrioritizedTasks()) {
            if ((t.getStartTime().isBefore(task.getStartTime()) && t.getEndTime().isAfter(task.getStartTime())) ||
                    (t.getStartTime().isBefore(t.getEndTime()) && t.getEndTime().isAfter(t.getEndTime()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addTask(Task task) {
        if (isTimeNotCrossing(task)) {
            taskMap.put(task.getId(), task);
        }

    }

    @Override
    public void addSubTask(Subtask subTask) {
        if (isTimeNotCrossing(subTask)) {
            subTaskMap.put(subTask.getId(), subTask);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (isTimeNotCrossing(epic)) {
            epicMap.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubTask(Subtask subtask, int id) {
        if (isTimeNotCrossing(subtask)) {
            historyManager.remove(id);
            subtask.setId(id);
            subTaskMap.put(id, subtask);
            historyManager.add(subtask);
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if (isTimeNotCrossing(task)) {
            historyManager.remove(id);
            task.setId(id);
            taskMap.put(id, task);
            historyManager.add(task);
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (isTimeNotCrossing(epic)) {
            Epic oldEpic = epicMap.get(id);
            if (oldEpic != null) {
                historyManager.remove(id);
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
            historyManager.add(epic);
        }
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
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpic(int id) {
        if (epicMap.containsKey(id)) {
            List<Subtask> oldEpicTasks = epicMap.get(id).getSubTasks();
            for (int i = 0; i < oldEpicTasks.size(); i++) {
                subTaskMap.remove(oldEpicTasks.get(i).getId());
                historyManager.remove(oldEpicTasks.get(i).getId());
            }
            epicMap.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeSubTask(int id) {
        int epicId = subTaskMap.get(id).getEpicId();
        epicMap.get(epicId).removeSubTask(getSubTask(id));
        if (subTaskMap.containsKey(id)) {
            subTaskMap.remove(id);
        }
        historyManager.remove(id);
    }

    @Override
    public void removeAll() {
        taskMap.clear();
        epicMap.clear();
        subTaskMap.clear();
    }

    @Override
    public void removeTasks() {
        taskMap.clear();
    }

    @Override
    public void removeEpics() {
        epicMap.clear();
    }

    @Override
    public void removeSubTasks() {
        subTaskMap.clear();
    }
}
