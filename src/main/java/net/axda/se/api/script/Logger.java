package net.axda.se.api.script;

import cn.nukkit.Server;
import cn.nukkit.utils.LogLevel;
import net.axda.se.api.API;
import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.HostAccess;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger extends API {

    private String title;
    private int consoleLevel = 4;
    private int fileLevel = 4;
    private int playerLevel = 4;
    private boolean isOpen = true;
    private File file;
    private cn.nukkit.utils.Logger logger = Server.getInstance().getLogger();

    @HostAccess.Export
    public void log(String... data) {
        print(ScriptLogLevel.STD, data);
    }

    @HostAccess.Export
    public void debug(String... data) {
        print(ScriptLogLevel.DEBUG, data);
    }

    @HostAccess.Export
    public void info(String... data) {
        print(ScriptLogLevel.INFO, data);
    }

    @HostAccess.Export
    public void warn(String... data) {
        print(ScriptLogLevel.WARN, data);
    }

    @HostAccess.Export
    public void error(String... data) {
        print(ScriptLogLevel.ERROR, data);
    }

    @HostAccess.Export
    public void fatal(String... data) {
        print(ScriptLogLevel.FATAL, data);
    }

    @HostAccess.Export
    public void setTitle(String title) {
        this.title = title;
    }

    @HostAccess.Export
    public void setLogLevel(int level) {
        this.consoleLevel = level;
        this.fileLevel = level;
        this.playerLevel = level;
    }

    @HostAccess.Export
    public void setConsole(boolean open, int level) {
        this.isOpen = open;
        this.consoleLevel = level;
    }

    @HostAccess.Export
    public void setConsole(boolean open) {
        this.setConsole(open, 4);
    }

    @HostAccess.Export
    public void setFile(String path, int level) {
        this.file = new File(path);
        this.fileLevel = level;
    }

    @HostAccess.Export
    public void setFile(String path) {
        this.file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.setFile(path, 4);
    }

    private StringBuilder getStringBuilder() {
        StringBuilder builder = new StringBuilder();
        if (title != null) {
            builder.append("[").append(title).append("] ");
        }
        return builder;
    }

    private String getContent(String[] data) {
        StringBuilder sb = getStringBuilder();
        for (String s : data) {
            sb.append(s);
        }
        return sb.toString();
    }

    private String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss %%");
        return now.format(formatter);
    }

    private void print(ScriptLogLevel level, String[] str) {
        String text = getContent(str);
        if (consoleLevel >= level.getValue()) {
            logger.log(level.getLevel(), text);
        }
        if (file != null) {
            if (fileLevel >= level.getValue()) {
                try {
                    if (level == ScriptLogLevel.STD) {
                        FileUtils.writeStringToFile(file, text + "\n", true);
                    } else {
                        FileUtils.writeStringToFile(file,"[" + getTime().replace("%%", level.name()) + "] " + text + "\n", true);
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public enum ScriptLogLevel {

        STD(-1, null),
        SLIENT(0, LogLevel.NONE),
        FATAL(1, LogLevel.CRITICAL),
        ERROR(2, LogLevel.ERROR),
        WARN(3, LogLevel.WARNING),
        INFO(4, LogLevel.INFO),
        DEBUG(5, LogLevel.DEBUG),
        ;

        public final int level;
        public final LogLevel logLevel;

        ScriptLogLevel(int i, LogLevel level) {
            this.level = i;
            this.logLevel = level;
        }

        public LogLevel getLevel() {
            return logLevel;
        }

        public int getValue() {
            return level;
        }
    }

}
