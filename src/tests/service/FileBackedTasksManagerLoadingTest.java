package service;

import model.Epic;
import model.Status;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileBackedTasksManagerLoadingTest {
    FileBackedTasksManager fileManger = Managers.getDefaultDB();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @AfterEach
    void afterEach() {
        fileManger.removeAll();
        historyManager.removeAll();
        fileManger.save();
    }

    @Test
    void readWriteTasksTest() throws IOException {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Task task1 = new Task("task1", "task1", Status.NEW);
        fileManger.addTask(task1);
        fileManger.addEpic(epic1);
        fileManger.getTask(task1.getId());
        fileManger.getEpic(epic1.getId());
        fileManger.readFile(fileManger.getUriDb());
        Assertions.assertEquals(task1.getTitle(), fileManger.getTask(task1.getId()).getTitle(),
                "Задача не сохранилась в файл");
        Assertions.assertEquals(epic1.getTitle(), fileManger.getEpic(epic1.getId()).getTitle(),
                "Задача не сохранилась в файл");
    }

    @Test
    void readWriteTest() throws IOException {
        FileBackedTasksManager testFileManger = new FileBackedTasksManager("test.txt");
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Task task1 = new Task("task1", "task1", Status.NEW);
        testFileManger.addTask(task1);
        testFileManger.addEpic(epic1);
        testFileManger.readFile(testFileManger.getUriDb());
        Assertions.assertEquals(task1.getTitle(), testFileManger.getTask(task1.getId()).getTitle(),
                "Задача не сохранилась в файл");
        Assertions.assertEquals(epic1.getTitle(), testFileManger.getEpic(epic1.getId()).getTitle(),
                "Задача не сохранилась в файл");
    }
}
