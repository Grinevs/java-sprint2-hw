package service;

public class InMemoryTasksManagerTest extends TaskManagerTest {

    public InMemoryTasksManagerTest() {
        super(Managers.getDefault());
    }
}
