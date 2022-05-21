package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    HttpResponse.BodyHandler<String> handler;
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;
    private String apiKey;
    private String url;

    public KVTaskClient(String url) {
        this.url = url;
        URI uri = URI.create(url + "/register");
        HttpRequest request = HttpRequest.
                newBuilder().
                GET().
                uri(uri).
                header("Accept", "text/html").
                build();
        client = HttpClient.newHttpClient();
        handler = HttpResponse.BodyHandlers.ofString();
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        apiKey = response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/save/" + key + "?API_KEY=" + apiKey);
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        client.send(request, handler);
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/load/"+key+"?API_KEY=" + apiKey);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        request = requestBuilder
                .GET()
                .uri(uri)
                .header("Accept", "application/json")
                .build();
        response = client.send(request, handler);
        return response.body();
    }
}
