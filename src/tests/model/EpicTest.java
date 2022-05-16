package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Managers;
import service.TaskManager;

class EpicTest {
    TaskManager manager = Managers.getDefault();
    Epic epic = new Epic("Title", "Description", Status.NEW);

    @BeforeEach
    void beforeEach() {
        manager.addEpic(epic);
    }

    @AfterEach
    void afterEach() {
        manager.removeEpic(epic.getId());
    }

    @Test
    void testStatusNoSub() {
        Assertions.assertEquals(Status.NEW, epic.getStatus(), "Статус не равен NEW");
    }

    @Test
    void testStatusNew() {
        Subtask subtask1 = new Subtask("subtask1", "subtask1", Status.NEW, epic);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "subtask2", Status.NEW, epic);
        manager.addSubTask(subtask2);
        Assertions.assertEquals(Status.NEW, epic.getStatus(), "Статус задачи не равен NEW");
    }

    @Test
    void testStatusDone() {
        Subtask subtask1 = new Subtask("subtask1", "subtask1", Status.DONE, epic);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "subtask2", Status.DONE, epic);
        manager.addSubTask(subtask2);
        Assertions.assertEquals(Status.DONE, epic.getStatus(), "Статус задачи не равен Done");
    }

    @Test
    void testStatusInProgress() {
        Subtask subtask1 = new Subtask("subtask1", "subtask1", Status.IN_PROGRESS, epic);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "subtask2", Status.IN_PROGRESS, epic);
        manager.addSubTask(subtask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус задачи не равен InProgress");
    }

    @Test
    void testStatusInProgressDifferentSubs() {
        Subtask subtask1 = new Subtask("subtask1", "subtask1", Status.NEW, epic);
        manager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("subtask2", "subtask2", Status.DONE, epic);
        manager.addSubTask(subtask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус задачи не равен InProgress");
    }
}