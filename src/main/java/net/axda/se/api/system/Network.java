package net.axda.se.api.system;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.MainLogger;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class Network {

    private static final Network instance = new Network();
    private final HttpClient httpClient;

    private Network() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public static Network getInstance() {
        return instance;
    }

    @HostAccess.Export
    public boolean httpGet(String url, Map<String, String> header, Value callback) {
        AsyncTask task = new AsyncTask() {
            @Override
            public void onRun() {
                API.setThreadName();
                HttpResponse<String> response = get(url, header);
                if (response == null) {
                    callback.executeVoid(-1, null);
                } else {
                    callback.executeVoid(response.statusCode(), response.body());
                }
            }
        };
        TaskHandler handler = Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
        task.setTaskId(handler.getTaskId());
        return true;
    }

    @HostAccess.Export
    public boolean httpGet(String url, Value callback) {
        return httpGet(url, null, callback);
    }

    @HostAccess.Export
    public boolean httpPost(String url, Map<String, String> header, String data, String type, Value callback) {
        AsyncTask task = new AsyncTask() {
            @Override
            public void onRun() {
                API.setThreadName();
                HttpResponse<String> response = post(url, header, data, type);
                if (response == null) {
                    callback.executeVoid(-1, null);
                } else {
                    callback.executeVoid(response.statusCode(), response.body());
                }
            }
        };
        TaskHandler handler = Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), task);
        task.setTaskId(handler.getTaskId());
        return true;
    }

    @HostAccess.Export
    public boolean httpPost(String url, String data, String type, Value callback) {
        return httpPost(url, null, data, type, callback);
    }

    public HttpResponse<String> get(String url, Map<String, String> header) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET();
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            HttpRequest request = requestBuilder.build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            ScriptLoader.getInstance().logException(new RuntimeException("Connect error: " + url, e));
            return null;
        }
    }

    public HttpResponse<String> post(String url, Map<String, String> header, String data, String type) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(data == null ? "" : data));
            // 设置Content-Type，默认为application/json
            if (type != null && !type.isEmpty()) {
                requestBuilder.header("Content-Type", type);
            } else {
                requestBuilder.header("Content-Type", "application/json");
            }
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            HttpRequest request = requestBuilder.build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            ScriptLoader.getInstance().logException(new RuntimeException("Connect error: " + url, e));
            return null;
        }
    }

}
