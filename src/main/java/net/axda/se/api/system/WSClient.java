package net.axda.se.api.system;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class WSClient extends WebSocketClient {

    private ScriptWSClient.ConnectAsyncTask task;

    public WSClient(URI serverUri, ScriptWSClient.ConnectAsyncTask task) {
        super(serverUri);
        this.task = task;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        task.execConnCallback(true);
    }

    @Override
    public void onMessage(String s) {
        task.call("onTextReceived", s);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        task.call("onBinaryReceived", bytes);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        task.call("onLostConnection", i);
    }

    @Override
    public void onError(Exception e) {
        task.execConnCallback(false);
        task.call("onError", e.getMessage());
    }
}
