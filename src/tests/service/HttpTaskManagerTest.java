package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.KVServer;

import java.io.IOException;

public  class HttpTaskManagerTest extends TaskManagerTest<TaskManager> {

    public HttpTaskManagerTest() {
        super(Managers.getDefaultHTTP());
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        KVServer kvs = new KVServer();
        kvs.start();
        kvs.removeData();
    }

    @Test
    void testLoadKVS() {
        Task task0 = new Task("Task1", "1задание", Status.NEW);
        Task task1 = new Task("Task2", "задание2", Status.DONE);
        manager.addTask(task0);
        manager.addTask(task1);
        Assertions.assertEquals(2, manager.getTaskMap().size(), "fail loading");
    }
}
