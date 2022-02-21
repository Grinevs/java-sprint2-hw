import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subTasks;

    public Epic(String title, String description, Status status, ArrayList<Subtask> subTasks) {
        super(title, description, status);
        this.subTasks = subTasks;
        super.status = updateStatus();
    }

    public void addSubTask(Subtask task) {
        subTasks.add(task);
        super.status = updateStatus();
    }

    public void removeSubTask(Subtask task) {
        subTasks.remove(task);
        super.status = updateStatus();
    }

    private Status updateStatus() {
        if (subTasks.size() == 0) {
            return Status.NEW;
        } else {
            int countNew = 0;
            int countDone = 0;
            for (int i = 0; i < subTasks.size(); i++) {
                if (subTasks.get(i).status == Status.NEW) {
                    countNew++;
                } else if (subTasks.get(i).status == Status.DONE) {
                    countDone++;
                }
            }
            if (countNew == subTasks.size()) {

                return Status.NEW;
            } else if (countDone == subTasks.size()) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
        }
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return super.toString() + "," + "Epic{" +
                "subTasks=" + subTasks +
                "} ";
    }
}
