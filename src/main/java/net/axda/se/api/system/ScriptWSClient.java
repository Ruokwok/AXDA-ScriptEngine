package net.axda.se.api.system;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.MainLogger;
import net.axda.se.AXDAScriptEngine;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.net.URI;
import java.util.HashMap;

/**
 *  脚本插件中使用的WebSocket客户端接口对象
 */
public class ScriptWSClient extends API implements AutoCloseable {

    @HostAccess.Export
    public final int Open = 0;

    @HostAccess.Export
    public final int Closing = 1;

    @HostAccess.Export
    public final int Closed = 2;

    @HostAccess.Export
    public int status = Closed;

    private ConnectAsyncTask task;
    private HashMap<String, Value> listeners = new HashMap<>();

    @HostAccess.Export
    public ScriptWSClient() {
        ScriptLoader.getInstance().putCloseable(this);
        listeners.put("onTextReceived", null);
        listeners.put("onBinaryReceived", null);
        listeners.put("onError", null);
        listeners.put("onLostConnection", null);
    }

    @HostAccess.Export
    public boolean connect(String url) {
        try {
            this.task = new ConnectAsyncTask(url, null);
            this.task.onRun();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @HostAccess.Export
    public boolean connectAsync(String url, Value callback) {
        try {
            this.task = new ConnectAsyncTask(url, callback);
            this.task.setTaskId(this.task.getTaskId());
            Server.getInstance().getScheduler().scheduleAsyncTask(AXDAScriptEngine.getPlugin(), this.task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @HostAccess.Export
    public boolean send(String message) {
        try {
            task.getConnect().send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @HostAccess.Export
    public boolean send(byte[] binary) {
        try {
            task.getConnect().send(binary);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @HostAccess.Export
    public void close() throws Exception {
        if (task != null) task.close();
    }

    @HostAccess.Export
    public void shutdown() throws Exception {
        if (task != null) task.close();
    }

    @HostAccess.Export
    public boolean listen(String event, Value callback) {
        if (!listeners.containsKey(event)) return false;
        listeners.put(event, callback);
        return true;
    }

    public class ConnectAsyncTask extends AsyncTask {

        private WSClient connect;
        private String url;
        private Value connCallback;

        public ConnectAsyncTask(String url, Value connCallback) {
            this.url = url;
            this.connCallback = connCallback;
        }

        @Override
        public void onRun() {
            Thread.currentThread().setName("js-" + getTaskId());
            this.connect = new WSClient(URI.create(url), this);
            this.connect.run();
        }

        public void close() {
            if (connect != null && connect.isOpen()) connect.close();
        }

        public WSClient getConnect() {
            return connect;
        }

        public void execConnCallback(boolean status) {
            try {
                if (status) ScriptWSClient.this.status = Open;
                if (connCallback != null) connCallback.executeVoid(status);
            } catch (Throwable t) {
                MainLogger.getLogger().logException(t);
            } finally {
                connCallback = null;
            }
        }

        public void call(String event, Object arg) {
            Value value = listeners.get(event);
            if (value != null) {
                try {
                    value.executeVoid(arg);
                } catch (Throwable t) {
                    MainLogger.getLogger().logException(t);
                }
            }
        }
    }

}
