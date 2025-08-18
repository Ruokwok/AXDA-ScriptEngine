package net.axda.se.api.game.data;

import net.axda.se.api.API;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.*;

/**
 * 此类包装了一个HashMap对象，以便在JavaScript中访问
 */
public class ProxyMap<V> extends API implements ProxyObject, Map<String, V> {

    private final HashMap<String, V> map;

    public ProxyMap() {
        this.map = new HashMap<>();
    }

    public ProxyMap(HashMap<String, V> map) {
        this.map = Objects.requireNonNull(map);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(String key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Object getMember(String key) {
        return map.get(key);
    }

    @Override
    public Object getMemberKeys() {
        Set<String> keys = keySet();
        return new ArrayList<>(keys);
    }

    @Override
    public boolean hasMember(String key) {
        return containsKey(key);
    }

    @Override
    public void putMember(String key, Value value) {
    }

    @Override
    public String toString() {
        return API.GSON.toJson(map);
    }
}
