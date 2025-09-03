package net.axda.se.api.data;

import cn.nukkit.Server;
import net.axda.se.ScriptLoader;
import net.axda.se.api.API;
import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KVDatabase extends API implements AutoCloseable {

    private DB db;

    @HostAccess.Export
    public KVDatabase(String url) {
        ScriptLoader.getInstance().putCloseable(this);
        try {
            File file = new File(url);
            if (!file.exists()) {
                FileUtils.createParentDirectories(file);
            }
            Iq80DBFactory factory = new Iq80DBFactory();
            this.db = factory.open(file, new Options().createIfMissing(true));
        } catch (Exception e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
        }
    }

    @HostAccess.Export
    @Override
    public void close() throws IOException {
        if (db != null) {
            this.db.close();
        }
    }

    @HostAccess.Export
    public boolean set(String key, Value value) {
        if (key == null) return false;
        try {
            byte[] bytes = serializeObject(value.as(Serializable.class));
            this.db.put(key.getBytes(), bytes);
            return true;
        } catch (IOException e) {
            Server.getInstance().getLogger().error("Failed to serialize object for key: " + key, e);
            return false;
        }
    }

    @HostAccess.Export
    public Object get(String key) {
        if (key == null) return null;
        byte[] bytes = this.db.get(key.getBytes());
        if (bytes == null) {
            return null;
        }
        try {
            Object o = deserializeObject(bytes);
            return o;
        } catch (IOException | ClassNotFoundException e) {
            Server.getInstance().getLogger().error("Failed to deserialize object for key: " + key, e);
            return null;
        }
    }

    @HostAccess.Export
    public void delete(String key) {
        if (key == null) return;
        this.db.delete(key.getBytes());
    }

    @HostAccess.Export
    public ProxyArray listKey() {
        if (db == null) return null;
        List<Object> keys = new ArrayList<>();
        try (var iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                byte[] keyBytes = iterator.peekNext().getKey();
                String key = new String(keyBytes);
                keys.add(key);
            }
        } catch (Exception e) {
            Server.getInstance().getLogger().error("Failed to list all keys", e);
        }
        return ProxyArray.fromList(keys);
    }

    public byte[] serializeObject(Serializable obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        return bos.toByteArray();
    }

    public Object deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}