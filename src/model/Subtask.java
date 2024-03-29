package model;

public class Subtask extends Task {
    private transient Epic epic;

    public Subtask(String title, String description, Status status, Epic epic) {
        super(title, description, status);
        this.epic = epic;
        epic.addSubTask(this);
    }

    public Subtask(String title, String description, Status status, Epic epic, int id) {
        super(title, description, status, id);
        this.epic = epic;
        epic.addSubTask(this);
    }

    public Epic getEpic() {
        return epic;
    }

    public int getEpicId() {
        return epic.getId();
    }

    @Override
    public String toString() {
        return super.toString() +
                "," + epic.getId();
    }
}