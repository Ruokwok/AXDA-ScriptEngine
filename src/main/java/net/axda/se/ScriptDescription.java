package net.axda.se;

import java.io.File;
import java.util.Map;

public class ScriptDescription {

    private String name;
    private String description;
    private Object[] version;
    private String versionStr;
    private Map<String, Object> others;
    private File file;

    public String getVersionStr() {
        return versionStr;
    }

    public String getName() {
        if (this.name == null) return file.getName();
        return name;
    }

    public void setName(String name) {
        if (this.name != null) {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersion(Object[] version) {
        this.version = version;
        if (version == null || version.length == 0) {
            versionStr = "null";
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean start = true;
        for (Object v : version) {
            if (start) {
                start = false;
            } else {
                sb.append(".");
            }
            sb.append(v.toString());
        }
        this.versionStr = sb.toString();
    }

    public Map<String, Object> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Object> info) {
        this.others = info;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
