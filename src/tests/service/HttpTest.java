package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTest extends TaskManagerTest {
    public HttpTest(TaskManager manager) {
        super(manager);
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        new KVServer().start();
    }

    @Test
    void testLoadCSV() {
        Task task0 = new Task("Task1", "1задание", Status.NEW);
        Task task1 = new Task("Task2", "задание2", Status.DONE);
        manager.addTask(task0);
        manager.addTask(task1);
        TaskManager newManager = new HTTPTaskManager("http://localhost:8078");
        assertEquals(2, newManager.getTaskMap().size(), "fail loading");
    }

}
