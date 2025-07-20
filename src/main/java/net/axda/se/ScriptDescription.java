package net.axda.se;

import java.util.Map;

public class ScriptDescription {

    public String name;
    public String description;
    public String[] version;
    public Map<String, Object> info;

    public String getVersionName() {
        if (version == null || version.length == 0) return "null";
        StringBuilder sb = new StringBuilder();
        boolean start = true;
        for (String v : version) {
            if (start) {
                start = false;
            } else {
                sb.append(".");
            }
            sb.append(v);
        }
        return sb.toString();
    }
}
