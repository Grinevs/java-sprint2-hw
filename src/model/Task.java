package model;

import service.Managers;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String title;
    protected String description;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.id = Managers.getCounterId().generateId();
        this.status = status;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public Task(String title, String description, Status status, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return id + "," + this.getClass().getSimpleName() + "," +
                title + "," + status +
                "," + description +
                "," + startTime +
                "," + duration.toSeconds();
    }
}
