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

    public static void remove(ScriptEngine engine) {
        map.remove(engine);
    }

    public static boolean call(String event, Object... args) {
        boolean b = true;
        for (ConcurrentHashMap<String, Listen> listenMap : map.values()) {
            if (listenMap.containsKey(event)) {
                b = listenMap.get(event).callEvent(args);
            }
        }
        return b;
    }

}
