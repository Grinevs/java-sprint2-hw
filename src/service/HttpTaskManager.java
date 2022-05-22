package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Epic;
import model.Subtask;
import model.Task;
import server.KVTaskClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient kvsClient;
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Gson gson = new Gson();

    public HttpTaskManager(String url) {
        super(url);
        this.kvsClient = new KVTaskClient(url);
        loadFromKVS();
    }

    public void loadFromKVS() {
        try {
            JsonObject data = gson.fromJson(kvsClient.load("KVS"), JsonObject.class);
            System.out.println(data);
            if (data == null) return;
            JsonArray tasks = data.getAsJsonArray("tasks");
            tasks.forEach(task -> {
                Task taskObj = gson.fromJson(task, Task.class);
                updateTask(taskObj, taskObj.getId());
            });
            JsonArray epics = data.getAsJsonArray("epics");
            epics.forEach(task -> {
                Epic taskObj = gson.fromJson(task, Epic.class);
                updateEpic(taskObj, taskObj.getId());
            });
            JsonArray subtasks = data.getAsJsonArray("subtasks");
            subtasks.forEach(task -> {
                Subtask taskObj = gson.fromJson(task, Subtask.class);
                updateSubTask(taskObj, taskObj.getId());
            });
            JsonArray history = data.getAsJsonArray("history");
            history.forEach(task -> historyManager.add(gson.fromJson(task, Task.class)));
            System.out.println("adad");
            System.out.println(Managers.getDefault().getEpicMap());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        Map<String, List<Task>> data = new HashMap<>();
        List<Task> tasks = new ArrayList<>();
        List<Task> epics = new ArrayList<>();
        tasks.addAll(getTaskMap().values());
        List<Task> subtasks = new ArrayList<>();
        epics.addAll(getEpicMap().values());
        subtasks.addAll(getSubTaskMap().values());
        data.put("epics", epics);
        data.put("tasks", tasks);
        data.put("subtasks", subtasks);
        data.put("history", Managers.getDefaultHistory().getHistory());
        try {
            kvsClient.put("KVS", gson.toJson(data));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
