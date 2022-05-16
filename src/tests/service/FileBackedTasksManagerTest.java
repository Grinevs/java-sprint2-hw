package service;

import model.Epic;
import model.Status;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileBackedTasksManagerTest extends TaskManagerTest {
    FileBackedTasksManager fileManger = new FileBackedTasksManager();
    HistoryManager historyManager = Managers.getDefaultHistory();

    public FileBackedTasksManagerTest() {
        super(Managers.getDefaultDB());
    }

    @AfterEach
    void afterEach() {
        manager.removeAll();
        historyManager.removeAll();
        fileManger.save();
    }

    @Test
    void readWriteTest() throws IOException {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Task task1 = new Task("task1", "task1", Status.NEW);
        manager.addTask(task1);
        manager.addEpic(epic1);
        manager.getTask(task1.getId());
        manager.getEpic(epic1.getId());
        fileManger.readFile(fileManger.getUriDb());
        Assertions.assertEquals(task1.getTitle(), manager.getTask(task1.getId()).getTitle(),
                "Задача не сохранилась в файл");
        Assertions.assertEquals(epic1.getTitle(), manager.getEpic(epic1.getId()).getTitle(),
                "Задача не сохранилась в файл");
    }
}
