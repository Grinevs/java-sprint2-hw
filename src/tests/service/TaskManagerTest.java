package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

abstract class TaskManagerTest<T extends TaskManager> {
    TaskManager manager;

    private static final String TEST_STRING_VALUE = "UPDATE";

    public TaskManagerTest(T manager) {
        this.manager = manager;
    }

    @AfterEach
    void afterEach() {
        manager.removeAll();
    }

    @Test
    void testAddTask() {
        Task task1 = new Task("task1", "task1", Status.NEW);
        manager.addTask(task1);
        Assertions.assertNotNull(manager.getTask(task1.getId()), "task не добавился");
    }

    @Test
    void testRemoveAllHistory() {
        manager.removeAll();
        Assertions.assertEquals(manager.getTaskMap().size(), 0,
                "Список Task должен быть пустой");
    }

    @Test
    void testAddTasks() {
        Task task1 = new Task("task1", "task1", Status.NEW);
        Task task2 = new Task("task2", "task2", Status.NEW);
        List<Task> expectedTask = List.of(task1, task2);
        manager.addTask(task1);
        manager.addTask(task2);
        Assertions.assertArrayEquals(expectedTask.toArray(), manager.getTaskMap().values().toArray(),
                "Массивы не равны");
    }

    @Test
    void testUpdatedTask() {
        Task task1 = new Task("task1", "task1", Status.NEW);
        Task task3 = new Task("UPDATE", "UPDATE", Status.IN_PROGRESS);
        manager.addTask(task1);
        task3.setId(task1.getId());
        Assertions.assertEquals(Status.NEW, task1.getStatus(), "задача уже изменена");
        Assertions.assertEquals(task1.getTitle(), "task1", "задача уже изменена");
        manager.updateTask(task3, task1.getId());
        Assertions.assertNotNull(manager.getTask(task1.getId()), "Метод вернул null");
        Assertions.assertEquals(task3.getId(), task1.getId(), "Id не совпадает");
        task1 = manager.getTask(task1.getId());
        Assertions.assertEquals(task1.getStatus(), Status.IN_PROGRESS, "Статус не обновился");
        Assertions.assertEquals(task1.getTitle(), TEST_STRING_VALUE, "Имя не обновилось");
    }

    @Test
    void testNullTaskAfterDelete() {
        Task task1 = new Task("task1", "task1", Status.NEW);
        manager.addTask(task1);
        manager.removeTask(task1.getId());
        Assertions.assertEquals(manager.getTask(task1.getId()), null,
                "Метод вернул значение, отличное от null");
    }

    @Test
    void testEqualsSubtasksArrays() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        Assertions.assertArrayEquals(manager.getSubTaskMap().values().toArray(), subtasks.toArray(),
                "Списки Subtask не равны");
    }

    @Test
    void testSubtasksRemove() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Subtask subtask1 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addEpic(epic1);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        manager.removeAll();
        Assertions.assertEquals(manager.getSubTaskMap().values().size(), 0,
                "Список должен быть пустой");
    }

    @Test
    void testSavedSubtask() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Subtask subtask1 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addEpic(epic1);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        Assertions.assertEquals(manager.getSubTask(subtask1.getId()), subtask1,
                "объект, не совпадает");
    }

    @Test
    void testNullAfterDelete() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Subtask subtask1 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addEpic(epic1);
        manager.addSubTask(subtask1);
        manager.removeSubTask(subtask1.getId());
        Assertions.assertEquals(manager.getSubTask(subtask1.getId()), null,
                "Subtask не удален");
    }

    @Test
    void testSizeAfterRemove() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Subtask subtask1 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addEpic(epic1);
        manager.addSubTask(subtask1);
        manager.removeEpic(epic1.getId());
        Assertions.assertEquals(manager.getEpicMap().values().size(), 0,
                "Epic должен быть пустой");
        Assertions.assertEquals(manager.getSubTaskMap().values().size(), 0,
                "Subtask должен быть пустой");
    }

    @Test
    void updateEpic() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        Epic epic3 = new Epic("UPDATE", "UPDATE", Status.NEW);
        manager.addEpic(epic1);
        epic3.setId(epic1.getId());
        Assertions.assertEquals(epic3.getStatus(), Status.NEW, "задача уже изменена");
        Assertions.assertEquals(epic3.getTitle(), TEST_STRING_VALUE, "задача уже изменена");
        manager.updateEpic(epic1, epic1.getId());
        Assertions.assertNotNull(manager.getEpic(epic1.getId()), "Метод вернул значение null");
        Assertions.assertEquals(epic3.getId(), epic1.getId(), "Id не совпадает");
        Assertions.assertEquals(epic3.getTitle(), TEST_STRING_VALUE, "Имя не обновилось");
    }

    @Test
    void testEpicRemove() {
        Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
        manager.addEpic(epic1);
        Subtask subtask3 = new Subtask("NEW", "NEW", Status.NEW, epic1);
        manager.addSubTask(subtask3);
        manager.removeEpic(epic1.getId());
        Assertions.assertEquals(manager.getEpic(epic1.getId()), null,
                "значение, отличное от null");
        Assertions.assertEquals(manager.getSubTask(subtask3.getId()), null,
                "значение, отличное от null");
    }
}

