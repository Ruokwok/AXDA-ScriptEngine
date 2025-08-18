package net.axda.se.api.system;

import net.axda.se.api.API;
import net.axda.se.api.game.data.ProxyMap;
import org.graalvm.polyglot.HostAccess;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class ScriptSystem extends API {

    @HostAccess.Export
    public String getTimeStr() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    @HostAccess.Export
    public Map<String, Integer> getTimeObj() {
        ProxyMap<Integer> map = new ProxyMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("Y", now.getYear());
        map.put("M", now.getMonthValue());
        map.put("D", now.getDayOfMonth());
        map.put("h", now.getHour());
        map.put("m", now.getMinute());
        map.put("s", now.getSecond());
        map.put("ms", now.getNano() / 1_000_000);
        return map;
    }

    @HostAccess.Export
    public String randomGuid() {
        return UUID.randomUUID().toString();
    }

}
