package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    void printAllTask();

    void printTasks();

    void printEpicTasks();

    void printSubTasks();

    Map<Integer, Task> getTaskMap();

    Map<Integer, Epic> getEpicMap();

    Map<Integer, Subtask> getSubTaskMap();

    void updateSubTask(Subtask subtask, int id);

    void updateTask(Task task, int id);

    void updateEpic(Epic epic, int id);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubTask(int id);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int id);

    void removeAll();
}
