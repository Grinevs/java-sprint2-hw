package service;

public class FileBackedTasksManagerTest extends TaskManagerTest {
    public FileBackedTasksManagerTest() {
        super(Managers.getDefaultDB());
    }
}
