package service;

import model.Epic;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

public class InMemoryHistoryManagerTest {
    TaskManager manager = Managers.getDefault();
    HistoryManager history = Managers.getDefaultHistory();
    Epic epic1 = new Epic("epic1", "epic1", Status.NEW);
    Epic epic2 = new Epic("epic2", "epic2", Status.NEW);

    @BeforeEach
    void beforeEach() {
        manager.addEpic(epic1);
        manager.addEpic(epic2);
    }

    @AfterEach
    void afterEach() {
        history.removeAll();
        manager.removeAll();
    }

    @Test
    void testSizeHistory() {
        Assertions.assertEquals(history.getHistory().size(), 0, "История не пуста");
    }

    @Test
    void testAddToHistory() {
        manager.getEpic(epic2.getId());
        manager.getEpic(epic1.getId());
        Assertions.assertEquals(history.getHistory().size(), 2, "Размер истории не равен 2");
    }
}
