package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            remove(0);
            history.add(task);
        }
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }


}
