package service;

import model.MyLinkedList;
import model.Task;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private MyLinkedList<Task> history = new MyLinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Task task) {
        if (history.size() < 10) {
            history.linkLast(task);
        } else {
            remove(0);
            history.linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }


}
