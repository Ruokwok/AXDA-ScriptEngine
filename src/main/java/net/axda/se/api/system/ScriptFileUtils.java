package net.axda.se.api.system;

import cn.nukkit.Server;
import net.axda.se.api.API;
import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.HostAccess;

import java.io.File;
import java.io.IOException;

public class ScriptFileUtils extends API {

    @HostAccess.Export
    public boolean createDir(String path) {
        try {
            File file = new File(path);
            FileUtils.forceMkdir(file);
            return file.exists();
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return false;
        }
    }

    @HostAccess.Export
    public boolean mkdir(String path) throws IOException {
        return createDir(path);
    }

    @HostAccess.Export
    public boolean delete(String path) {
        return new File(path).delete();
    }

    @HostAccess.Export
    public boolean exists(String path) {
        return new File(path).exists();
    }

    @HostAccess.Export
    public boolean copy(String form, String to) {
        try {
            FileUtils.copyFile(new File(form), new File(to));
            return true;
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return false;
        }
    }

    @HostAccess.Export
    public boolean move(String form, String to) {
        try {
            FileUtils.moveFile(new File(form), new File(to));
            return true;
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return false;
        }
    }

    @HostAccess.Export
    public boolean rename(String form, String to) {
        return move(form, to);
    }

    @HostAccess.Export
    public int getFileSize(String path) {
        File file = new File(path);
        if (!file.exists()) return -2;
        if (file.isDirectory()) return -1;
        return (int) file.length();
    }

    @HostAccess.Export
    public boolean checkIsDir(String path) {
        File file = new File(path);
        if (!file.exists()) return false;
        return file.isDirectory();
    }

    @HostAccess.Export
    public String[] getFilesList(String path) {
        File file = new File(path);
        if (!file.exists()) return null;
        if (file.isDirectory()) {
            return file.list();
        }
        return null;
    }

    @HostAccess.Export
    public String readFrom(String path) {
        try {
            return FileUtils.readFileToString(new File(path), "utf-8");
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return null;
        }
    }

    @HostAccess.Export
    public boolean writeTo(String path, String content) {
        try {
            FileUtils.writeStringToFile(new File(path), content, "utf-8");
            return true;
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return false;
        }
    }

    @HostAccess.Export
    public boolean writeLine(String path, String content) {
        try {
            FileUtils.writeStringToFile(new File(path), content + "\n", "utf-8", true);
            return true;
        } catch (IOException e) {
            Server.getInstance().getLogger().error(e.getMessage(), e);
            return false;
        }
    }

}
