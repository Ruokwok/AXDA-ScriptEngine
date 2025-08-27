package net.axda.se.api.game;

import org.graalvm.polyglot.HostAccess;

import java.util.HashMap;

public class ScriptCommand {

    private String cmd;
    private String desc;
    private int prem;
    private int flag;
    private String[] alias;
    private HashMap<String, String[]> _enum = new HashMap<>();
    private HashMap<String, String[]> params = new HashMap<>();

    public ScriptCommand(String cmd, String desc, int prem, int flag, String[] alias) {
        this.cmd = cmd;
        this.desc = desc;
        this.prem = prem;
        this.flag = flag;
        this.alias = alias;
    }

    @HostAccess.Export
    public boolean setAlias(String... alias) {
        this.alias = alias;
        return true;
    }

    @HostAccess.Export
    public String setEnum(String name, String[] values) {
        this._enum.put(name, values);
        return name;
    }

    @HostAccess.Export
    public boolean mandatory(String name, int type, String enumName, String identifier, int enumOptions) {
        String key = name;
        if (type == 14 && enumName != null) {
            if (this._enum.containsKey(enumName)) {
                key = enumName;
            } else return false;
        }
        this.params.put(key, new String[] {name, String.valueOf(type), enumName, identifier, String.valueOf(enumOptions), "false"});
        return true;
    }

    @HostAccess.Export
    public boolean optional(String name, int type, String enumName, String identifier, int enumOptions) {
        String key = name;
        if (type == 14 && enumName != null) {
            if (this._enum.containsKey(enumName)) {
                key = enumName;
            } else return false;
        }
        this.params.put(key, new String[] {name, String.valueOf(type), enumName, identifier, String.valueOf(enumOptions), "true"});
        return true;
    }

    @HostAccess.Export
    public boolean optional(String name, int type, String enumName) {
        return optional(name, type, enumName, enumName, 0);
    }

    @HostAccess.Export
    public boolean optional(String name, int type) {
        return optional(name, type, null);
    }

    @HostAccess.Export
    public boolean overload(String[] params) {
        return true;
    }

}
