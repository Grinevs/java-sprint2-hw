import service.Managers;

public class InMemoryTasksManagerTest extends TaskManagerTest {

    public InMemoryTasksManagerTest() {
        super(Managers.getDefault());
    }
}
