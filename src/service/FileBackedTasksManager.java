package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static final String WORKDIR = System.getProperty("user.dir");

    public static Path getUriDb() {
        Path dbPath = Paths.get(WORKDIR, "src", "db", "db.csv");
        return dbPath;
    }

    public static Task taskFromString(String value) {
        String[] words = value.split(",");
        CounterId.addUsedId(Integer.parseInt(words[0]));
        if (words[1].equalsIgnoreCase("Task")) {
            Task task = new Task(words[2], words[4], Status.valueOf(words[3]), Integer.parseInt(words[0]));
            Managers.getDefault().getTaskMap().put(task.getId(), task);
            return task;
        }
        if (words[1].equalsIgnoreCase("Epic")) {
            Task task = new Epic(words[2], words[4], Status.valueOf(words[3]), Integer.parseInt(words[0]));
            Managers.getDefault().getEpicMap().put(task.getId(), (Epic) task);
            return task;
        }
        if (words[1].equalsIgnoreCase("Subtask")) {
            Task task = new Subtask(words[2], words[4], Status.valueOf(words[3]),
                    Managers.getDefault().getEpic(Integer.parseInt(words[5])), Integer.parseInt(words[0]));
            Managers.getDefault().getSubTaskMap().put(task.getId(), (Subtask) task);
            return task;
        }
        return null;
    }

    public static void historyFromString(String value) {
        String[] words = value.split(",");
        for (int i = 0; i < words.length; i++) {
            Task task = Managers.getDefault().getAllTaskMap().get(Integer.parseInt(words[i]));
            Managers.getDefaultHistory().add(task);
        }
    }

    public static void readFile() {
        Managers.getDefault().removeAll();
        Managers.getDefaultHistory().removeAll();
        try (FileReader reader = new FileReader(String.valueOf(getUriDb()));
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(String.valueOf(getUriDb()));
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (Task task : Managers.getDefaultDB().getAllTaskMap().values()) {
                bw.write(task.toString() + "\n");
            }
            bw.newLine();
            List<Task> taskMap = Managers.getDefaultHistory().getHistory();
            for (Task task : taskMap) {
                bw.write(task.getId() + ",");
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
