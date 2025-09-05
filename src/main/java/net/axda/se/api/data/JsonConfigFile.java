package net.axda.se.api.data;

import cn.nukkit.utils.MainLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.axda.se.api.API;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConfigFile extends API {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File file;
    private Map<String, Object> data;

    @HostAccess.Export
    public JsonConfigFile(String path, String def) throws IOException {
        this.file = new File(path);
        if (!file.exists()) {
            FileUtils.forceMkdirParent(file);
            write(def);
        }
        data = gson.fromJson(read(), Map.class);
        if (data == null) data = new HashMap<>();
    }

    @HostAccess.Export
    public JsonConfigFile(String path) throws IOException {
        this(path, "");
    }

    @HostAccess.Export
    public Object init(String key, Value value) {
        if (data.containsKey(key)) {
            return get(key);
        } else {
            data.put(key, value.as(Object.class));
            write(gson.toJson(data));
            return value;
        }
    }

    @HostAccess.Export
    public Object get(String key, Value value) {
        if (data.containsKey(key)) {
            Object obj = data.get(key);
            if (obj instanceof Double d) {
                if (d == Math.floor(d) && !Double.isInfinite(d)) {
                    if (d <= Integer.MAX_VALUE && d >= Integer.MIN_VALUE) {
                        return d.intValue();
                    }
                }
            } else if (obj instanceof Map<?,?> m) {
                return new HashMap<>(m);
            } else if (obj instanceof List<?> l) {
                ArrayList<Object> list = new ArrayList<>(l);
                return ProxyArray.fromList(list);
            }
            return obj;
        } else {
            return value;
        }
    }

    @HostAccess.Export
    public Object get(String key) {
        return get(key, null);
    }

    @HostAccess.Export
    public boolean set(String key, Value value) {
        data.put(key, value.as(Object.class));
        write(gson.toJson(data));
        return true;
    }

    @HostAccess.Export
    public boolean delete(String key) {
        data.remove(key);
        write(gson.toJson(data));
        return true;
    }

    public void write(String json) {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            IOUtils.write(json, stream, "UTF-8");
            stream.close();
        } catch (Exception e) {
            MainLogger.getLogger().logException(e);
        }
    }

    public String read() {
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            MainLogger.getLogger().logException(e);
            return null;
        }
    }

}
