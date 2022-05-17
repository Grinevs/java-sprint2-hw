package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static final String WORK_DIR = System.getProperty("user.dir");
    private static final String LINE_DELIMITER = "\n";
    private static final String ITEM_DELIMITER = ",";
    private static final String TASK_STRING = "Task";
    private static final String EPIC_STRING = "Epic";
    private static final String SUBTASK_STRING = "Subtask";
    private static final String SOURCE_DIR = "src";
    private static final String DB_DIR = "db";
    private static final int TASK_ID = 0;
    private static final int TASK_TYPE = 1;
    private static final int TASK_NAME = 2;
    private static final int TASK_STATUS = 3;
    private static final int TASK_INFO = 4;
    private static final int SUBTASK_EPIC = 7;
    private static final int START_TIME = 5;
    private static final int DURATION = 6;

    private final TaskManager taskManager = Managers.getDefault();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final CounterId counterId = Managers.getCounterId();
    private final Path dbPath;

    public FileBackedTasksManager(String dbFileName) {
        this.dbPath = Paths.get(WORK_DIR, SOURCE_DIR, DB_DIR, dbFileName);
        readFile(this.dbPath);
    }

    public Path getUriDb() {
        return dbPath;
    }

    public Task taskFromString(String value) {
        String[] words = value.split(ITEM_DELIMITER);
        counterId.addUsedId(Integer.parseInt(words[TASK_ID]));
        if (words[TASK_TYPE].equalsIgnoreCase(TASK_STRING)) {
            Task task = new Task(words[TASK_NAME], words[TASK_INFO], Status.valueOf(words[TASK_STATUS]),
                    Integer.parseInt(words[TASK_ID]));
            task.setStartTime(LocalDateTime.parse(words[START_TIME]));
            task.setDuration(Duration.ofSeconds(Integer.parseInt(words[DURATION])));
            taskManager.getTaskMap().put(task.getId(), task);
            return task;
        }
        if (words[TASK_TYPE].equalsIgnoreCase(EPIC_STRING)) {
            Task task = new Epic(words[TASK_NAME], words[TASK_INFO], Status.valueOf(words[TASK_STATUS]),
                    Integer.parseInt(words[TASK_ID]));
            task.setStartTime(LocalDateTime.parse(words[START_TIME]));
            task.setDuration(Duration.ofSeconds(Integer.parseInt(words[DURATION])));
            taskManager.getEpicMap().put(task.getId(), (Epic) task);
            return task;
        }
        if (words[TASK_TYPE].equalsIgnoreCase(SUBTASK_STRING)) {
            Task task = new Subtask(words[TASK_NAME], words[TASK_INFO], Status.valueOf(words[TASK_STATUS]),
                    taskManager.getEpic(Integer.parseInt(words[SUBTASK_EPIC])),
                    Integer.parseInt(words[TASK_ID]));
            task.setStartTime(LocalDateTime.parse(words[START_TIME]));
            task.setDuration(Duration.ofSeconds(Integer.parseInt(words[DURATION])));
            taskManager.getSubTaskMap().put(task.getId(), (Subtask) task);
            return task;
        }
        return null;
    }

    public void historyFromString(String value) {
        historyManager.removeAll();
        String[] words = value.split(ITEM_DELIMITER);
        for (int i = 0; i < words.length; i++) {
            Task task = taskManager.getAllTaskMap().get(Integer.parseInt(words[i]));
            historyManager.add(task);
        }
    }

    public void readFile(Path path) {
        try (FileReader reader = new FileReader(String.valueOf(path));
             BufferedReader br = new BufferedReader(reader)) {
            while (br.ready()) {
                String line = br.readLine().trim();
                if (!line.isBlank()) {
                    taskFromString(line);
                } else {
                    String lastLine = br.readLine();
                    if (!(lastLine == null)) {
                        historyFromString(lastLine);
                    }
                    break;
                }
                historyManager.removeAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(String.valueOf(getUriDb()));
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Task task : taskManager.getAllTaskMap().values()) {
                bw.write(task.toString() + LINE_DELIMITER);
            }
            bw.newLine();
            List<Task> taskMap = historyManager.getHistory();
            for (Task task : taskMap) {
                bw.write(task.getId() + ITEM_DELIMITER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubTask(Subtask subtask, int id) {
        super.updateSubTask(subtask, id);
        save();
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubTask(int id) {
        super.removeSubTask(id);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public Task getTask(int id) {
        if (super.getTask(id) != null) {
            save();
            return super.getTask(id);
        }
        return null;
    }

    @Override
    public Epic getEpic(int id) {
        if (super.getEpic(id) != null) {
            save();
            return super.getEpic(id);
        }
        return null;
    }

    @Override
    public Subtask getSubTask(int id) {
        if (super.getSubTask(id) != null) {
            save();
            return super.getSubTask(id);
        }
        return null;
    }
}
