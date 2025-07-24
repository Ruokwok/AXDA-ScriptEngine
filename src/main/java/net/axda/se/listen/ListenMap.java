package net.axda.se.listen;

import net.axda.se.ScriptEngine;

import java.util.concurrent.ConcurrentHashMap;

public class ListenMap {

    private static ConcurrentHashMap<ScriptEngine, ConcurrentHashMap<String, Listen>> map = new ConcurrentHashMap<>();

    public static boolean put(String event, Listen listen) {
        if (ListenEvent.getAllEvents().contains(event)) {
            if (map.containsKey(listen.getEngine())) {
                map.get(listen.getEngine()).put(event, listen);
            } else {
                ConcurrentHashMap<String, Listen> listenMap = new ConcurrentHashMap<>();
                listenMap.put(event, listen);
                map.put(listen.getEngine(), listenMap);
            }
            return true;
        }
        return false;
    }

}
