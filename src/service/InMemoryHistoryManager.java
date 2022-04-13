package service;

import model.MyLinkedList;
import model.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private MyLinkedList<Task> history = new MyLinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public void removeAll() {
        history.clear();
    }

}
