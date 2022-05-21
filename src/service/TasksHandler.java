package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TasksHandler implements HttpHandler {
    private TaskManager manager = Managers.getDefault();
    private HistoryManager history = Managers.getDefaultHistory();
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();
        String arrUri[] = uri.split("/");
        String response = "";
        int id = 0;

        if (arrUri[arrUri.length - 1].contains("?")) {
            id = Integer.parseInt(arrUri[arrUri.length - 1].split("=")[1]);
        }

        switch (method) {
            case "GET":
                response = (arrUri.length == 2) ?
                        gson.toJson(manager.getAllTaskMap().values()) :
                        "";
                if (arrUri[2].equals("epic")) {
                    response = (id != 0) ? gson.toJson(manager.getEpic(id)) :
                            gson.toJson(manager.getEpicMap().values());
                }
                if (arrUri[2].equals("task")) {
                    response = (id != 0) ? gson.toJson(manager.getTask(id)) :
                            gson.toJson(manager.getTaskMap().values());
                }
                if (arrUri[2].equals("subtask")) {
                    response = (id != 0) ? gson.toJson(manager.getSubTask(id)) :
                            gson.toJson(manager.getSubTaskMap().values());
                    System.out.println(manager.getSubTaskMap().values());
                }
                if (arrUri[2].equals("history")) {
                    gson.toJson(history.getHistory());
                }
                if (arrUri.length == 5 && arrUri[2].equals("subtask") && arrUri[3].equals("epic")) {
                    response = gson.toJson(manager.getEpic(manager.getSubTask(id).getEpicId()));
                }
                httpExchange.sendResponseHeaders(response.equals("") ? 400 : 200, 0);
                break;
            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                String body = new String(inputStream.readAllBytes());
                if (arrUri[2].equals("task")) {
                    Task task = gson.fromJson(body, Task.class);
                    if (id == 0) {
                        manager.addTask(task);
                    } else {
                        task.setId(id);
                        manager.updateTask(task, task.getId());
                    }
                }
                if (arrUri[2].equals("epic")) {
                    Epic task = gson.fromJson(body, Epic.class);
                    if (id == 0) {
                        manager.addEpic(task);
                    } else {
                        task.setId(id);
                        manager.updateEpic(task, task.getId());
                    }
                }
                if (arrUri[2].equals("subtask")) {
                    Subtask task = gson.fromJson(body, Subtask.class);
                    if (id == 0) {
                        manager.addSubTask(task);
                    } else {
                        task.setId(id);
                        manager.updateSubTask(task, task.getId());
                    }
                }
                httpExchange.sendResponseHeaders(201, 0);
                break;
            case "DELETE":
                if (arrUri[2].equals("task")) {
                    if ((id == 0)) manager.removeTasks();
                    else manager.removeTask(id);
                }
                if (arrUri[2].equals("epic")) {
                    if ((id == 0)) manager.removeEpics();
                    else manager.removeEpic(id);
                }
                if (arrUri[2].equals("subtask")) {
                    if ((id == 0)) manager.removeSubTasks();
                    else manager.removeSubTask(id);
                }
                httpExchange.sendResponseHeaders(205, 0);
                break;
            default:
        }

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }


}

