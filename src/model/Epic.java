package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Subtask> subTasks;
    protected LocalDateTime endTime = getStartTime();

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        this.subTasks = new ArrayList<>();
        super.status = Status.NEW;
    }

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
        this.subTasks = new ArrayList<>();
        super.status = Status.NEW;
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
        }
        int countNew = 0;
        int countDone = 0;
        LocalDateTime firstTaskTime = getStartTime();
        LocalDateTime lastTaskTime = endTime;
        for (int i = 0; i < subTasks.size(); i++) {
            Subtask currentSub = subTasks.get(i);
            firstTaskTime = firstTaskTime.isBefore(currentSub.getStartTime()) ?
                    firstTaskTime : currentSub.getStartTime();
            lastTaskTime = lastTaskTime.isAfter(currentSub.getStartTime().plus(currentSub.getDuration())) ?
                    lastTaskTime : currentSub.getStartTime().plus(currentSub.getDuration());
            if (subTasks.get(i).status == Status.NEW) {
                countNew++;
            } else if (subTasks.get(i).status == Status.DONE) {
                countDone++;
            }
        }
        this.setStartTime(firstTaskTime);
        this.endTime=lastTaskTime;
        this.setDuration(Duration.between(firstTaskTime,lastTaskTime));
        if (countNew == subTasks.size()) return Status.NEW;
        if (countDone == subTasks.size()) return Status.DONE;
        return Status.IN_PROGRESS;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Subtask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
